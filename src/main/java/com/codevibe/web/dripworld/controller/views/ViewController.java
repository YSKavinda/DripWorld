package com.codevibe.web.dripworld.controller.views;

import com.codevibe.web.dripworld.entities.StockEntity;
import com.codevibe.web.dripworld.service.StockService;
import jakarta.inject.Inject;
import jakarta.servlet.RequestDispatcher;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import org.glassfish.jersey.server.mvc.Viewable;

@Path("/")
public class ViewController {

    @Inject
    StockService stockService;

    @GET
    public Viewable index(){return new Viewable("/frontend/home");}

    @Path("/home")
    @GET
    public Viewable home(){return new Viewable("/frontend/home");}


    @Path("/login")
    @GET
    public Viewable login() {return new Viewable("/frontend/login");}


    @Path("/register")
    @GET
    public Viewable register() {return new Viewable("/frontend/register");}

//    @Path("/search")
//    @GET
//    public Viewable search() {return new Viewable("/frontend/search");}

    @Path("/store")
    @GET
    public Viewable store() {return new Viewable("/frontend/store");}

    @Path("/order-history")
    @GET
    public Viewable checkout() {return new Viewable("/frontend/checkout");}

    @Path("/single-product/{stock_id}")
    @GET
    public Viewable productView(
            @PathParam("stock_id") Long stockId) {
           return new Viewable("/frontend/single-product-view",stockId);
    }

    @Path("/cart")
    @GET
    public Viewable cart() {return new Viewable("/frontend/cart");}
}
