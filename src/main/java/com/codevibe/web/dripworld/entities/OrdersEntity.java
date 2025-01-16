package com.codevibe.web.dripworld.entities;

import com.codevibe.web.dripworld.constants.OrderStatus;
import com.codevibe.web.dripworld.constants.PaymentMethods;
import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

@Entity
@Table(name = "orders", schema = "dripwrld_db", catalog = "")
public class OrdersEntity extends BaseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "invoice_code",unique = true)
    private String code;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "payment_method")
    private PaymentMethods paymentMethod;


    @Column(name = "delivery_cost",precision = 10,scale = 2,nullable = false)
    private BigDecimal delivery;

    @Column(name = "total",precision = 10,scale = 2,nullable = false)
    private BigDecimal total;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus status;
    @OneToMany(mappedBy = "ordersByOrdersId")
    private List<OrderItemsEntity> orderItemsById = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "users_id", referencedColumnName = "id", nullable = false)
    private UsersEntity usersByUsersId;

//    @OneToMany(mappedBy = "ordersById")
//    private List<PaymentsEntity> payments;

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    @Column(name = "order_date",updatable = false)
    private Date orderDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<OrderItemsEntity> getOrderItemsById() {
        return orderItemsById;
    }

    public void setOrderItemsById(List<OrderItemsEntity> orderItemsById) {
        this.orderItemsById = orderItemsById;
    }

    public UsersEntity getUsersByUsersId() {
        return usersByUsersId;
    }

    public void setUsersByUsersId(UsersEntity usersByUsersId) {
        this.usersByUsersId = usersByUsersId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BigDecimal getDelivery() {
        return delivery;
    }

    public void setDelivery(BigDecimal delivery) {
        this.delivery = delivery;
    }

//    public List<PaymentsEntity> getPayments() {
//        return payments;
//    }
//
//    public void setPayments(List<PaymentsEntity> payments) {
//        this.payments = payments;
//    }

    public PaymentMethods getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethods paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
