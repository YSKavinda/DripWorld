//package com.codevibe.web.dripworld.entities;
//
//import com.codevibe.web.dripworld.constants.PaymentMethods;
//import jakarta.persistence.*;
//import java.math.BigDecimal;
//import java.util.Date;
//
//
//
//@Entity
//@Table(name = "payments", schema = "dripwrld_db", catalog = "")
//public class PaymentsEntity extends BaseEntity {
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Id
//    @Column(name = "id")
//    private Long id;
//
//    @Column(name = "invoice_code")
//    private String invoice_code;
//    @Enumerated(value = EnumType.STRING)
//    @Column(name = "payment_method")
//    private PaymentMethods paymentMethod;
//
//    @Column(name = "amount",precision = 10,scale = 2,nullable = false)
//    private BigDecimal amount;
//
//    @Column(name = "payment_date",updatable = false)
//    private Date paymentDate;
//
//    @ManyToOne
//    @JoinColumn(name = "orders_id")
//    private OrdersEntity ordersById;
//
//    public OrdersEntity getOrdersById() {
//        return ordersById;
//    }
//
//    public void setOrdersById(OrdersEntity ordersById) {
//        this.ordersById = ordersById;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public PaymentMethods getPaymentMethod() {
//        return paymentMethod;
//    }
//
//    public void setPaymentMethod(PaymentMethods paymentMethod) {
//        this.paymentMethod = paymentMethod;
//    }
//
//    public BigDecimal getAmount() {
//        return amount;
//    }
//
//    public void setAmount(BigDecimal amount) {
//        this.amount = amount;
//    }
//
//    public Date getPaymentDate() {
//        return paymentDate;
//    }
//
//    public void setPaymentDate(Date paymentDate) {
//        this.paymentDate = paymentDate;
//    }
//
//    public String getInvoice_code() {
//        return invoice_code;
//    }
//
//    public void setInvoice_code(String invoice_code) {
//        this.invoice_code = invoice_code;
//    }
//
//
//
//
//
//}
