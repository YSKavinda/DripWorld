package com.codevibe.web.dripworld.handler;

import com.codevibe.web.dripworld.dao.ResponseDAO;
import com.codevibe.web.dripworld.handler.types.*;
import com.codevibe.web.dripworld.util.ResponseUtil;
import io.fusionauth.jwt.JWTException;
import io.fusionauth.jwt.JWTExpiredException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;


import java.util.logging.Level;
import java.util.logging.Logger;

@Provider
public class CommonExceptionHandler implements ExceptionMapper<Throwable> {
    private static final Logger LOGGER = Logger.getLogger(CommonExceptionHandler.class.getName());

    @Override
    public Response toResponse(Throwable ex){
        LOGGER.log(Level.SEVERE,ex.getMessage(),ex);
        System.out.println(ex);


       if(ex instanceof DBFailureException)
           return ResponseUtil.generate(Response.Status.BAD_REQUEST,null, ex.getMessage());

       if(ex instanceof JWTExpiredException)
           return ResponseUtil.generate(Response.Status.UNAUTHORIZED,null,"Token Expired");

       if(ex instanceof JWTException)
           return ResponseUtil.generate(Response.Status.UNAUTHORIZED,null,"Authorization Failed");

       if(ex instanceof AuthorizationFailedException)
           return ResponseUtil.generate(Response.Status.UNAUTHORIZED,null,"Authorization Failed");

       if(ex instanceof UserNotFoundException)
           return ResponseUtil.generate(Response.Status.UNAUTHORIZED,null,"User Not Found");

       if(ex instanceof ValidationFailedException)
           return ResponseUtil.generate(Response.Status.BAD_REQUEST,null,"Invalid Data");

       if(ex instanceof ProcessFailedException)
           return ResponseUtil.generate(Response.Status.INTERNAL_SERVER_ERROR,null,"Logic Error");

        return ResponseUtil.generate(Response.Status.INTERNAL_SERVER_ERROR,new ResponseDAO(500,"INTERNAL SERVER ERR","failed"));



    }

}
