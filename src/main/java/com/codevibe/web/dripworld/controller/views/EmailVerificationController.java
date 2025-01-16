package com.codevibe.web.dripworld.controller.views;

import com.codevibe.web.dripworld.service.AuthService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

@Path("/verify")
public class EmailVerificationController {
    @Inject
    private AuthService authService;

    @GET
    public Response verify(@QueryParam("token") String token) {

        System.out.println("TOKEN"+token);
        return authService.verify(token);
    }
}
