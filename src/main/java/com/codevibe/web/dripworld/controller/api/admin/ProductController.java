package com.codevibe.web.dripworld.controller.api.admin;

import com.codevibe.web.dripworld.annotations.ApiSecure;
import com.codevibe.web.dripworld.service.ProductService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataParam;

@ApiSecure
@Path("/api/v1/admin/products")
public class ProductController {
    @Inject
    private ProductService service;

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(
            @FormDataParam("name") String name,
            @FormDataParam("description") String description,
            @FormDataParam("categoryId") Long categoryId,
            @FormDataParam("image[]") FormDataBodyPart bodyPart
    )
    {
        return service.save(name,description,categoryId,bodyPart);
    }
}
