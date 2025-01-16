package com.codevibe.web.dripworld.service;

import com.codevibe.web.dripworld.constants.FilterFormats;
import com.codevibe.web.dripworld.dao.CategoryDAO;
import com.codevibe.web.dripworld.dao.ProductDAO;
import com.codevibe.web.dripworld.dao.ResponseDAO;
import com.codevibe.web.dripworld.entities.CartEntity;
import com.codevibe.web.dripworld.entities.CategoriesEntity;
import com.codevibe.web.dripworld.entities.ProductEntity;
import com.codevibe.web.dripworld.repositories.CategoryRepositories;
import com.codevibe.web.dripworld.repositories.ProductRepository;
import com.codevibe.web.dripworld.util.Env;
import com.codevibe.web.dripworld.util.ResponseUtil;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.media.multipart.ContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductService {
    private static final String SERVER_URL = Env.get("server.url");
  @Inject
    ProductRepository productRepository;

  @Inject
    CategoryRepositories categoryRepositories;

    @Inject
    private FileUploadService fileUploadService;


    public Response search(String key, Long categoryId, FilterFormats filterFormats,Integer pageNo,Integer pageSize){
        if(pageNo == null || pageNo < 1)
            pageNo = 1;
        if(pageSize == null|| pageSize <1)
            pageSize = 10;
        if(key == null)
            key = "";
        String query = "SELECT p FROM ProductEntity p WHERE (p.name LIKE '%"+key+"%')";
        if(categoryId!=null)
            query+= "AND p.categoriesByCategoriesId.id="+categoryId;
        if(filterFormats != null)
            switch (filterFormats){
                case A_Z:
                    query+="ORDER BY p.name ASC";
                    break;
                case Z_A:
                    query+="ORDER BY p.name DESC";
                case LATEST:
                    query+="ORDER BY p.createdAt DESC";
                    break;
            }
        System.out.println(query);

        final int totalElements = productRepository.findAll(query).size();
        return ResponseUtil.generate(
                Response.Status.OK,
                new ResponseDAO(
                        200,
                        produceDAOList(productRepository.findAll(query,pageNo,pageSize)),
                        "success",
                        (long)totalElements,
                        (int)Math.ceil((double) totalElements/pageSize)
                )
        );

    }


//    public Response search(String key, Long categoryId, FilterFormats filterFormats,Integer pageNo,Integer pageSize){
//        if(pageNo == null || pageNo < 1)
//            pageNo = 1;
//        if(pageSize == null|| pageSize <1)
//            pageSize = 10;
//        if(key == null)
//            key = "";
//        String query = "SELECT p FROM ProductEntity p WHERE (p.name LIKE '%"+key+"%')";
//        if(categoryId!=null)
//            query+= "AND p.categoriesByCategoriesId.id="+categoryId;
//        if(filterFormats != null)
//            switch (filterFormats){
//
//                case A_Z:
//                    query+="ORDER BY p.name ASC";
//                    break;
//                case Z_A:
//                    query+="ORDER BY p.name DESC";
//                case L_H:
//                    query+="ORDER BY p.price ASC";
//                case H_L:
//                    query+="ORDER BY p.price DESC";
//                case LATEST:
//                    query+="ORDER BY p.createdAt DESC";
//                    break;
//            }
//        System.out.println(query);
//
//        final int totalElements = productRepository.findAll(query).size();
//        return ResponseUtil.generate(
//                Response.Status.OK,
//                new ResponseDAO(
//                        200,
//                        produceDAOList(productRepository.findAll(query,pageNo,pageSize)),
//                        "success",
//                        (long)totalElements,
//                        (int)Math.ceil((double) totalElements/pageSize)
//                )
//        );
//
//    }



    public List<ProductDAO> produceDAOList(List<ProductEntity> list){
        final List<ProductDAO> daoList = new ArrayList<>();
        if(list!=null)
            list.forEach(product->{
                final ProductDAO dao = new ProductDAO();
                dao.setId(product.getId());
                dao.setName(product.getName());
                dao.setDescription(product.getDescription());
                dao.setCategoriesByCategoriesId(
                        new CategoryDAO(
                                product.getCategoriesByCategoriesId().getId(),
                                product.getCategoriesByCategoriesId().getName(),
                                product.getCategoriesByCategoriesId().getDescription()
                        )
                );
                final List<String> images = new ArrayList<>();
                if(product.getImages()!=null){
                    product.getImages().forEach(img->{
                        images.add(SERVER_URL+img);
                    });
                }
                dao.setImages(images);
                daoList.add(dao);

            });
        return daoList;
    }

    public List<String> generateImageUrls(List<String> images){
        final List<String> list = new ArrayList<>();
        if(images!=null)
            images.forEach(img->list.add(SERVER_URL+img));
        return list;
    }


  public Response save(String name, String description , Long categoryId, FormDataBodyPart bodyPart){
      if(name == null|| name.isBlank() || categoryId == null || bodyPart == null)
          return ResponseUtil.generate(Response.Status.BAD_REQUEST,null,"Invalid Product Data");

      if(productRepository.findByName(name).isPresent())
          return ResponseUtil.generate(Response.Status.BAD_REQUEST,null,"Product Already Exist");


       final CategoriesEntity category = categoryRepositories.findById(categoryId).get();
       if(category == null)
           return ResponseUtil.generate(Response.Status.INTERNAL_SERVER_ERROR,null,"Category Not Found");

       final ProductEntity product = new ProductEntity();
       product.setName(name);
       product.setDescription(description);
       product.setCategoriesByCategoriesId(category);

       final List<String> images = new ArrayList<>();
       bodyPart.getParent().getBodyParts().forEach(
               part->{
                   if(!part.getMediaType().equals(MediaType.TEXT_PLAIN_TYPE) && !part.getMediaType().equals(MediaType.TEXT_PLAIN)){
                       final InputStream is = part.getEntityAs(InputStream.class);
                       final ContentDisposition metaFile = part.getContentDisposition();
                       System.out.println(metaFile);
                       final FileUploadService.FileItem fileItem = fileUploadService.upload("prod",is,metaFile);
                       images.add(fileItem.getPath());
                   }
               }
       );

       product.setImages(images);
      productRepository.save(product);

      return ResponseUtil.generate(Response.Status.OK,null,"Product is Created Successfully");
  }

  public ProductRepository getProductRepository (){return productRepository;}

}
