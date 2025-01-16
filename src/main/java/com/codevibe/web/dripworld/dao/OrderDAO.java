package com.codevibe.web.dripworld.dao;

import com.codevibe.web.dripworld.constants.OrderStatus;
import com.codevibe.web.dripworld.constants.PaymentMethods;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class OrderDAO {

    private Long id;

    private String code;

    private PaymentMethods paymentMethod;

    private BigDecimal delivery;

    private BigDecimal total;

    private OrderStatus status;

    private List<OrderItemsDAO> orderItems;

    private UserDAO userDAO;

    private Date orderDate;

    public OrderDAO(Long id, String code, PaymentMethods paymentMethod, BigDecimal delivery, BigDecimal total, OrderStatus status, List<OrderItemsDAO> orderItems, UserDAO userDAO, Date orderDate) {
        this.id = id;
        this.code = code;
        this.paymentMethod = paymentMethod;
        this.delivery = delivery;
        this.total = total;
        this.status = status;
        this.orderItems = orderItems;
        this.userDAO = userDAO;
        this.orderDate = orderDate;
    }

    public OrderDAO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public PaymentMethods getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethods paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public BigDecimal getDelivery() {
        return delivery;
    }

    public void setDelivery(BigDecimal delivery) {
        this.delivery = delivery;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public List<OrderItemsDAO> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemsDAO> orderItems) {
        this.orderItems = orderItems;
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }
}
