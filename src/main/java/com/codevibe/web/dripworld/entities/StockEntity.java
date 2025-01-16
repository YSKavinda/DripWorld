package com.codevibe.web.dripworld.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "stock", schema = "dripwrld_db", catalog = "")
public class StockEntity extends BaseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "qty")
    private int qty;

    @OneToMany(mappedBy = "stockByStockId")
    private Collection<OrderItemsEntity> orderItemsById;
    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
    private ProductEntity productByProductId;

    @OneToMany(mappedBy = "stockByStockId")
    private List<CartEntity> cartsById;

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


    public Collection<OrderItemsEntity> getOrderItemsById() {
        return orderItemsById;
    }

    public void setOrderItemsById(Collection<OrderItemsEntity> orderItemsById) {
        this.orderItemsById = orderItemsById;
    }

    public ProductEntity getProductByProductId() {
        return productByProductId;
    }

    public void setProductByProductId(ProductEntity productByProductId) {
        this.productByProductId = productByProductId;
    }
    public Collection<CartEntity> getCartsById() {
        return cartsById;
    }

    public void setCartsById(List<CartEntity> cartsById) {
        this.cartsById = cartsById;
    }
}
