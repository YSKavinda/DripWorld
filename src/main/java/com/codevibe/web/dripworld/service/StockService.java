package com.codevibe.web.dripworld.service;

import com.codevibe.web.dripworld.constants.FilterFormats;
import com.codevibe.web.dripworld.dao.CategoryDAO;
import com.codevibe.web.dripworld.dao.ProductDAO;
import com.codevibe.web.dripworld.dao.ResponseDAO;
import com.codevibe.web.dripworld.dao.StockDAO;
import com.codevibe.web.dripworld.entities.ProductEntity;
import com.codevibe.web.dripworld.entities.StockEntity;
import com.codevibe.web.dripworld.handler.types.ValidationFailedException;
import com.codevibe.web.dripworld.repositories.ProductRepository;
import com.codevibe.web.dripworld.repositories.StockRepository;
import com.codevibe.web.dripworld.util.ResponseUtil;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StockService {

    @Inject
    StockRepository stockRepository;

    @Inject
    ProductRepository productRepository;

    public List<StockDAO> getStockFromProduct(Long productId) {
        if (productId == null) {
            throw new ValidationFailedException("Product Validation Failed");
        } else {

            final Optional<ProductEntity> optional = productRepository.findById(productId);

            //optional getting check it provides the product
            if (optional.isPresent()) {

                List<StockDAO> stockList = new ArrayList<>();
                stockRepository.findByProduct(optional.get()).forEach((stock -> {
                    final StockDAO obj = new StockDAO(stock.getId(), stock.getPrice(), stock.getQty(), new ProductDAO(
                            stock.getProductByProductId().getId(),
                            stock.getProductByProductId().getName(),
                            stock.getProductByProductId().getDescription(),
                            stock.getProductByProductId().getImages(),
                            new CategoryDAO(
                                    stock.getProductByProductId().getCategoriesByCategoriesId().getId(),
                                    stock.getProductByProductId().getCategoriesByCategoriesId().getName(),
                                    stock.getProductByProductId().getCategoriesByCategoriesId().getDescription()
                            )
                    ));
                    stockList.add(obj);
                }));

                return stockList;

            }
            throw new ValidationFailedException("Data loading failed");
        }

    }

    public StockDAO getProductDetailsFromStockId(Long id){
        if(id == null)
            return null;


          final StockEntity stock = stockRepository.findById(id).get();

          System.out.println(stock.getProductByProductId().getName()+"STOCK+NAME");

          final StockDAO dao = new StockDAO();
          dao.setId(stock.getId());
          dao.setQty(stock.getQty());
          dao.setPrice(stock.getPrice());

          final ProductDAO pdao = new ProductDAO();
          pdao.setId(stock.getProductByProductId().getId());
          pdao.setImages(stock.getProductByProductId().getImages());
          pdao.setName(stock.getProductByProductId().getName());
          pdao.setDescription(stock.getProductByProductId().getDescription());
          pdao.setCategoriesByCategoriesId(
                  new CategoryDAO(stock.getProductByProductId().getCategoriesByCategoriesId().getId(),
                  stock.getProductByProductId().getCategoriesByCategoriesId().getName(),
                  stock.getProductByProductId().getCategoriesByCategoriesId().getDescription()));

          dao.setProductByProductId(pdao);

          return dao;




    }


    public Response searchByProduct(String key, Long categoryId, FilterFormats filterFormats, Integer pageNo, Integer pageSize) {
        if (pageNo == null || pageNo < 1)
            pageNo = 1;
        if (pageSize == null || pageSize < 1)
            pageSize = 10;
        if (key == null)
            key = "";
        String query = "SELECT s FROM StockEntity s WHERE (s.productByProductId.name LIKE '%" + key + "%')";
        if (categoryId != null)
            query += "AND (s.productByProductId.categoriesByCategoriesId.id=" + categoryId+")";
        if (filterFormats != null)
            switch (filterFormats) {

                case A_Z:
                    query += "ORDER BY s.productByProductId.name ASC";
                    break;
                case Z_A:
                    query += "ORDER BY s.productByProductId.name DESC";
                case L_H:
                    query += "ORDER BY s.price ASC";
                case H_L:
                    query += "ORDER BY s.price DESC";
                case LATEST:
                    query += "ORDER BY s.createdAt DESC";
                    break;
            }
        System.out.println(query);
        final int totalElements = stockRepository.findAll(query).size();
        return ResponseUtil.generate(
                Response.Status.OK,
                new ResponseDAO(
                        200,
                        produceDAOList(stockRepository.findAll(query,pageNo,pageSize)),
                        "success",
                        (long)totalElements,
                        (int)Math.ceil((double) totalElements/pageSize)
                )
        );
    }

    public List<StockDAO> produceDAOList(List<StockEntity> list){
        final List<StockDAO> daoList = new ArrayList<>();
        if(list!=null)
            list.forEach(
                    stock->{
                        System.out.println(stock.getId()+"Stock ID");
                        final StockDAO dao = new StockDAO();
                        dao.setId(stock.getId());
                        dao.setPrice(stock.getPrice());
                        dao.setQty(stock.getQty());
                        dao.setProductByProductId(
                                new ProductDAO(
                                        stock.getProductByProductId().getId(),
                                        stock.getProductByProductId().getName(),
                                        stock.getProductByProductId().getDescription(),
                                        stock.getProductByProductId().getImages(),
                                        new CategoryDAO(
                                                stock.getProductByProductId().getCategoriesByCategoriesId().getId(),
                                                stock.getProductByProductId().getCategoriesByCategoriesId().getName(),
                                                stock.getProductByProductId().getCategoriesByCategoriesId().getDescription()
                                               )

                                )
                        );
                        daoList.add(dao);
                    }
            );
        return daoList;
    }

    public Response save(BigDecimal price,int qty,Long productId){
         if(price == null || qty == 0 || productId == null)
             ResponseUtil.generate(Response.Status.BAD_REQUEST,null,"Invalid Stock Data");
         if(!productRepository.findById(productId).isPresent())
             ResponseUtil.generate(Response.Status.BAD_REQUEST,null,"Product Not Found");

         final ProductEntity product = productRepository.findById(productId).get();

         final StockEntity new_stock = new StockEntity();
         new_stock.setPrice(price);
         new_stock.setQty(qty);
         new_stock.setProductByProductId(product);

        stockRepository.save(new_stock);

        return ResponseUtil.generate(Response.Status.OK,null,"Stock Successfully Added");
    }


    public StockRepository getStockRepository(){return stockRepository;}
}
