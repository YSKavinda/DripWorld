package com.codevibe.web.dripworld.util;

import com.codevibe.web.dripworld.dao.ResponseDAO;
import jakarta.ws.rs.core.Response;

public final class ResponseUtil {
    public static Response generate(Response.Status status, Object entity) {
        return Response.status(status)
                .entity(entity)
                .type("application/json")
                .build();
    }

    public static Response generate(Response.Status status, Object data, String message) {
        return generate(
                status,
                new ResponseDAO(status.getStatusCode(), data, message)
        );
    }
}