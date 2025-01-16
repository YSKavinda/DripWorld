package com.codevibe.web.dripworld.controller.api;

import com.codevibe.web.dripworld.annotations.ApiSecure;
import com.codevibe.web.dripworld.dao.CartDAO;
import com.codevibe.web.dripworld.dao.ResponseDAO;
import com.codevibe.web.dripworld.service.CartService;
import com.codevibe.web.dripworld.util.ResponseUtil;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
@ApiSecure
@Path("/api/v1/cart")
public class CartController {

    @Inject
    private CartService cartService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByUser(@Context HttpServletRequest request){
        final List<CartDAO> allByUser = cartService.getAllByUser(request);
        return ResponseUtil.generate(Response.Status.OK,
                new ResponseDAO(200,
                        allByUser,
                        "success",
                        (long)allByUser.size(),
                        null
                )
        );
    }

    @Path("/add")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response add(CartDAO cartDAO, @Context HttpServletRequest request){
        System.out.println("Stock ID at Cart" + cartDAO.getStockId());
        return cartService.save(cartDAO,request);
    }

    @Path("/{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteFromCart(@PathParam("id") Long id,@Context HttpServletRequest request){
        cartService.deleteById(id,request);
        return ResponseUtil.generate(Response.Status.OK,null,"Item is Removed from cart");
    }

    @Path("/qty")
    @PATCH
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response quantityUpdate(CartDAO cartDAO,@Context HttpServletRequest request){
       return cartService.changeQuantity(cartDAO,request);
    }

    @Path("/clear-my-cart")
    @DELETE
    public Response clearCart(@Context HttpServletRequest request){
        cartService.clearCart(request);
        return ResponseUtil.generate(Response.Status.OK,null,"Cart Cleared");
    }


}
