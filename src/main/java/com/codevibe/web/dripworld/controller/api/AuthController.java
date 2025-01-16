package com.codevibe.web.dripworld.controller.api;

import com.codevibe.web.dripworld.dao.AuthResponseDAO;
import com.codevibe.web.dripworld.dao.TokenDAO;
import com.codevibe.web.dripworld.dao.UserDAO;
import com.codevibe.web.dripworld.dao.UserDetails;
import com.codevibe.web.dripworld.service.AuthService;
import com.codevibe.web.dripworld.service.UserService;
import com.codevibe.web.dripworld.util.JwtTokenUtil;
import com.codevibe.web.dripworld.util.ResponseUtil;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/v1/auth")
public class AuthController {

    @Inject
    private AuthService authService;

    @Path("/signIn")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response signIn(UserDAO userDAO,@Context HttpServletRequest request){
        return authService.signIn(userDAO,request);
    }

    @Path("/signUp")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response signUp(UserDAO userDAO){
        return authService.signUp(userDAO);
    }

    @Path("/refresh-token")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response refreshToken(TokenDAO tokenDAO) {
        System.out.println("REFRESH TOKEN :"+tokenDAO.getRefreshToken()+"ACCESS TOKEN :"+tokenDAO.getAccessToken());
        return authService.refreshToken(tokenDAO);
    }

    @Path("/logout")
    @GET
    public Response logout(@Context HttpServletRequest request) {
        request.getSession().invalidate();
        return ResponseUtil.generate(Response.Status.OK, null);
    }

}
