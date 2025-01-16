package com.codevibe.web.dripworld.controller.api.admin;


import com.codevibe.web.dripworld.annotations.ApiSecure;
import com.codevibe.web.dripworld.service.ProductService;
import com.codevibe.web.dripworld.service.StockService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.media.multipart.FormDataParam;

import java.math.BigDecimal;

@ApiSecure
@Path("/api/v1/admin/stock")
public class StockController {

    @Inject
    private StockService service;

    @POST
    public Response save(
            @FormParam("price") Double price,
            @FormParam("qty") int qty,
            @FormParam("product_id") Long productId)
    {
        return service.save(BigDecimal.valueOf(price),qty,productId);
    }


}
