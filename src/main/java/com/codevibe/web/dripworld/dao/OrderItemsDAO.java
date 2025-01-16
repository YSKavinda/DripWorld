package com.codevibe.web.dripworld.dao;

import java.math.BigDecimal;

public class OrderItemsDAO {
    private Long id;
    private int qty;
    private BigDecimal price;
    private OrderDAO order;
    private StockDAO stockDAO;

    public OrderItemsDAO() {
    }

    public OrderItemsDAO(Long id, int qty, BigDecimal price, OrderDAO order, StockDAO stockDAO) {
        this.id = id;
        this.qty = qty;
        this.price = price;
        this.order = order;
        this.stockDAO = stockDAO;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public OrderDAO getOrder() {
        return order;
    }

    public void setOrder(OrderDAO order) {
        this.order = order;
    }

    public StockDAO getStockDAO() {
        return stockDAO;
    }

    public void setStockDAO(StockDAO stockDAO) {
        this.stockDAO = stockDAO;
    }
}
