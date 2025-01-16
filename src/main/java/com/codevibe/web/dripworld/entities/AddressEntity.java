package com.codevibe.web.dripworld.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "address",schema = "dripwrld_db")
public class AddressEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String street;
    private String city;
    private String zipCode;
    @ManyToOne
    @JoinColumn(name = "orders_id")
    private OrdersEntity order;

    public AddressEntity() {
    }

    public AddressEntity(String street, String city, String zipCode, OrdersEntity order) {
        this.street = street;
        this.city = city;
        this.zipCode = zipCode;
        this.order = order;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public OrdersEntity getOrder() {
        return order;
    }

    public void setOrder(OrdersEntity order) {
        this.order = order;
    }
}
