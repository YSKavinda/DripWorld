package com.codevibe.web.dripworld.controller.api.admin;

import com.codevibe.web.dripworld.annotations.ApiSecure;
import com.codevibe.web.dripworld.dao.CategoryDAO;
import com.codevibe.web.dripworld.service.CategoryService;
import com.codevibe.web.dripworld.util.ResponseUtil;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@ApiSecure
@Path("/api/v1/admin/categories")
public class CategoryController {
    @Inject
    private CategoryService categoryService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(CategoryDAO categoryDAO){
        System.out.println(categoryDAO.getName()+"Category Name");
        categoryService.save(categoryDAO);
        return ResponseUtil.generate(Response.Status.OK,null ,"Category Created Successfully");
    }
}
