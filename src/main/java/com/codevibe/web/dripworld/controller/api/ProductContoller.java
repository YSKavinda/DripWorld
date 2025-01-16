package com.codevibe.web.dripworld.controller.api;

import com.codevibe.web.dripworld.constants.FilterFormats;
import com.codevibe.web.dripworld.service.ProductService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

@Path("/api/v1/products")
public class ProductContoller {

    @Inject
    private ProductService productService;

    @Path("/search")
    @GET
    public Response response(
            @QueryParam("key") String key,
            @QueryParam("category") Long categoryId,
            @QueryParam("orderBy")FilterFormats filterFormats,
            @QueryParam("pageNo") Integer pageNo,
            @QueryParam("pageSize") Integer pageSize){

        return productService.search(key,categoryId,filterFormats,pageNo,pageSize);


    }

}
