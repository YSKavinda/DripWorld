package com.codevibe.web.dripworld.controller.api;

import com.codevibe.web.dripworld.annotations.ApiSecure;
import com.codevibe.web.dripworld.constants.OrderStatus;
import com.codevibe.web.dripworld.constants.PaymentMethods;
import com.codevibe.web.dripworld.service.OrderService;
import com.codevibe.web.dripworld.service.ProductService;
import com.codevibe.web.dripworld.util.ResponseUtil;
import jakarta.annotation.Nullable;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;

@ApiSecure
@Path("/api/v1/orders")
public class OrdersController {
    @Inject
    private OrderService orderService;

    @GET
    public Response getAllByLoggedUser(@Context HttpServletRequest request){

        return ResponseUtil.generate(
                Response.Status.OK,
                orderService.getAllOrdersByCurrentUser(request),
                "Success"
        );
    }

    @Path("/{id}")
    @GET
    public Response getById(@PathParam("id") Long id,@Context HttpServletRequest request){
        if(orderService.getByIdAndUser(id,request)!=null){

           return ResponseUtil.generate(Response.Status.BAD_REQUEST,null,"Wrong user");

        }else{
            return ResponseUtil.generate(
                    Response.Status.OK,
                    orderService.getByIdAndUser(id,request),
                    "Success"
                    );
        }

    }


    @Path("/code/{code}")
    @GET
    public Response getOrderByCode(@PathParam("code") String code,@Context HttpServletRequest request){
        return ResponseUtil.generate(
                Response.Status.OK,
                orderService.getByCodeAndUser(code,request),
                "success"
        );
    }

    @Path("/{id}/cancel")
    @PATCH
    public Response cancelOrder(@PathParam("id") Long orderId,@Context  HttpServletRequest request){
        orderService.updateStatus(orderId, OrderStatus.canceled,request);
        return ResponseUtil.generate(
            Response.Status.OK,
                null,
                "Order is Cancelled"
        );
    }


    @Path("/cart-checkout")
    @POST
    public Response checkoutFromCart(
            @FormParam("invoiceId")@Nullable String invoiceId,
            @FormParam("payment_method") PaymentMethods paymentMethod,
            @FormParam("city") String city,
            @FormParam("street") String street,
            @FormParam("zipCode") String zip,
            @Context HttpServletRequest request
            ){
        return orderService.ordersFromCart(request,invoiceId,paymentMethod,city,street,zip);
    }

    @Path("/cart/on-checkout")
    @GET
    public Response onCheckout(@Context HttpServletRequest request) {
        return orderService.onCheckout(request);
    }

}
