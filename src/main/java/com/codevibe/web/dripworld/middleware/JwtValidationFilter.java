package com.codevibe.web.dripworld.middleware;

import com.codevibe.web.dripworld.dao.ResponseDAO;
import com.codevibe.web.dripworld.repositories.UserRepository;
import com.codevibe.web.dripworld.util.JwtTokenUtil;
import com.codevibe.web.dripworld.util.ResponseUtil;
import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;
import java.util.logging.Logger;

@Provider
@Priority(1)
public class JwtValidationFilter implements ContainerRequestFilter {
    private static final Logger LOGGER = Logger.getLogger(JwtValidationFilter.class.getName());
    private static final String[] WHITE_LIST = {
            "api/v1/auth/",
            "uploads/",
            "home/",
            "api/v1",
            ""
    };

    private static final String ADMIN_PATH = "api/v1/admin/";
    @Inject
    private JwtTokenUtil jwtTokenUtil;
    @Inject
    private UserRepository userRepository;
    @Context
    private HttpServletRequest request;


    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        System.out.println("Jwt Validation Filter :::::");

        final String path = requestContext.getUriInfo().getPath();
        System.out.println("PATH ::: " + path);

        if (isWhiteListUrl(path))
            return;

        final String authHeader = requestContext.getHeaders().getFirst("Authorization");
        if (authHeader == null) {
            System.out.println("Authorization headers not found ::::");
            requestContext.abortWith(
                    ResponseUtil.generate(Response.Status.UNAUTHORIZED, new ResponseDAO(401, null, "Unauthorized"))
            );
        } else if (!authHeader.startsWith("Bearer ")) {
            requestContext.abortWith(
                    ResponseUtil.generate(Response.Status.UNAUTHORIZED, new ResponseDAO(401, null, "Invalid token"))
            );
        } else {

            final String token = authHeader.split(" ")[1];
            System.out.println("TOKEN :::" + token);

//            final User user = userRepository
//                    .findByEmail(jwtTokenUtil.getUsernameFromToken(token))
//                    .orElseThrow(UserNotFoundException::new);
//
//            if (path.contains(ADMIN_PATH) && !user.getUserRole().equals(UserRole.ADMIN))
//                requestContext.abortWith(
//                        ResponseUtil.generate(Response.Status.FORBIDDEN, null, "Access Forbidden")
//                );

        }

    }

    private boolean isWhiteListUrl(String path) {
        for (String pattern : WHITE_LIST) {
            if (path.contains(pattern))
                return true;
        }
        return false;
    }

}
