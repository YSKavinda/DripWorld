package com.codevibe.web.dripworld.controller.api;

import com.codevibe.web.dripworld.constants.FilterFormats;
import com.codevibe.web.dripworld.dao.StockDAO;
import com.codevibe.web.dripworld.service.StockService;
import com.codevibe.web.dripworld.util.ResponseUtil;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

@Path("/api/v1/stock")
public class StockController {

    @Inject
    private StockService service;

    @Path("/search")
    @GET
    public Response response(
            @QueryParam("key") String key,
            @QueryParam("category") Long categoryId,
            @QueryParam("sortBy") FilterFormats filterFormats,
            @QueryParam("pageNo") Integer pageNo,
            @QueryParam("pageSize") Integer pageSize) {

        return service.searchByProduct(key, categoryId, filterFormats, pageNo, pageSize);

    }

    @Path("/single-product")
    @GET
    public Response get(@QueryParam("stock_id") Long stockId) {

        System.out.println("CONTROLLER" + stockId);

        return ResponseUtil.generate(Response.Status.OK, service.getProductDetailsFromStockId(stockId), "Success");

    }


}
