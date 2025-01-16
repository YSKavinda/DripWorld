package com.codevibe.web.dripworld.controller.api;

import com.codevibe.web.dripworld.service.CategoryService;
import com.codevibe.web.dripworld.util.ResponseUtil;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/api/v1/categories")
public class CategoryController {
    @Inject
    private CategoryService categoryService;

    @GET
    public Response getAll(){
        return ResponseUtil.generate(Response.Status.OK,categoryService.getAll(),"success");
    }

}
