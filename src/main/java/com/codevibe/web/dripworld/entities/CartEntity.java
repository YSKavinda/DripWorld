package com.codevibe.web.dripworld.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "cart", schema = "dripwrld_db")
public class CartEntity extends BaseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "quantity")
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "users_id", referencedColumnName = "id", nullable = false)
    private UsersEntity usersByUsersId;
//    @ManyToOne
//    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
//    private ProductEntity productByProductId;
    @ManyToOne
    @JoinColumn(name = "stock_id", referencedColumnName = "id", nullable = false)
    private StockEntity stockByStockId;

    public CartEntity(){

    }

    public CartEntity(Integer quantity, UsersEntity usersByUsersId, StockEntity stockByStockId) {
        this.quantity = quantity;
        this.usersByUsersId = usersByUsersId;
        this.stockByStockId = stockByStockId;
    }

    public StockEntity getStockByStockId() {
        return stockByStockId;
    }

    public void setStockByStockId(StockEntity stockByStockId) {
        this.stockByStockId = stockByStockId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public UsersEntity getUsersByUsersId() {
        return usersByUsersId;
    }

    public void setUsersByUsersId(UsersEntity usersByUsersId) {
        this.usersByUsersId = usersByUsersId;
    }

//    public ProductEntity getProductByProductId() {
//        return productByProductId;
//    }
//
//    public void setProductByProductId(ProductEntity productByProductId) {
//        this.productByProductId = productByProductId;
//    }
}
