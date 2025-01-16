package com.codevibe.web.dripworld.dao;

import com.codevibe.web.dripworld.entities.ProductEntity;


import java.math.BigDecimal;

public class StockDAO {

    private int id;

    private BigDecimal price;

    private int qty;

    private ProductDAO productByProductId;

    public StockDAO(int id, BigDecimal price, int qty, ProductDAO productByProductId) {
        this.id = id;
        this.price = price;
        this.qty = qty;
        this.productByProductId = productByProductId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public ProductDAO getProductByProductId() {
        return productByProductId;
    }

    public void setProductByProductId(ProductDAO productByProductId) {
        this.productByProductId = productByProductId;
    }

    public StockDAO() {
    }
}
