package com.codevibe.web.dripworld.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "order_items", schema = "dripwrld_db", catalog = "")
public class OrderItemsEntity extends BaseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;
    @Basic
    @Column(name = "qty")
    private int qty;
    @Basic
    @Column(name = "price")
    private BigDecimal price;
    @ManyToOne
    @JoinColumn(name = "orders_id", referencedColumnName = "id", nullable = false)
    private OrdersEntity ordersByOrdersId;
    @ManyToOne
    @JoinColumn(name = "stock_id", referencedColumnName = "id", nullable = false)
    private StockEntity stockByStockId;

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

    public OrdersEntity getOrdersByOrdersId() {
        return ordersByOrdersId;
    }

    public void setOrdersByOrdersId(OrdersEntity ordersByOrdersId) {
        this.ordersByOrdersId = ordersByOrdersId;
    }

    public StockEntity getStockByStockId() {
        return stockByStockId;
    }

    public void setStockByStockId(StockEntity stockByStockId) {
        this.stockByStockId = stockByStockId;
    }
}
