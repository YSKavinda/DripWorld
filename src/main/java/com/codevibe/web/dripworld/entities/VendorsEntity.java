//package com.codevibe.web.dripworld.entities;
//
//import jakarta.persistence.*;
//
//import java.io.Serializable;
//import java.sql.Timestamp;
//import java.util.Collection;
//import java.util.Objects;
//
//@Entity
//@Table(name = "vendors", schema = "dripwrld_db", catalog = "")
//public class VendorsEntity extends BaseEntity {
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Id
//    @Column(name = "id")
//    private int id;
//
//    @OneToOne
//    @JoinColumn(name = "users_id")
//    private UsersEntity usersEntity;
//
//    @Column(name = "business_name")
//    private String businessName;
//
//    @Column(name = "business_email")
//    private String businessEmail;
//
//    @Column(name = "business_phone")
//    private String businessPhone;
//
//
//    @Column(name = "is_active")
//    private Byte isActive;
//
//    @OneToMany(mappedBy = "vendorsByVendorsId")
//    private Collection<ProductEntity> productsById;
//    @ManyToOne
//    @JoinColumn(name = "users_id", referencedColumnName = "id", nullable = false)
//    private UsersEntity usersByUsersId;
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public UsersEntity getUser() {
//        return usersEntity;
//    }
//
//    public void setUsersId(UsersEntity usersEntity) {
//        this.usersEntity = usersEntity;
//    }
//
//    public String getBusinessName() {
//        return businessName;
//    }
//
//    public void setBusinessName(String businessName) {
//        this.businessName = businessName;
//    }
//
//    public String getBusinessEmail() {
//        return businessEmail;
//    }
//
//    public void setBusinessEmail(String businessEmail) {
//        this.businessEmail = businessEmail;
//    }
//
//    public String getBusinessPhone() {
//        return businessPhone;
//    }
//
//    public void setBusinessPhone(String businessPhone) {
//        this.businessPhone = businessPhone;
//    }
//
//    public Byte getIsActive() {
//        return isActive;
//    }
//
//    public void setIsActive(Byte isActive) {
//        this.isActive = isActive;
//    }
//
//    public Collection<ProductEntity> getProductsById() {
//        return productsById;
//    }
//
//    public void setProductsById(Collection<ProductEntity> productsById) {
//        this.productsById = productsById;
//    }
//
//    public UsersEntity getUsersByUsersId() {
//        return usersEntity;
//    }
//
//    public void setUsersByUsersId(UsersEntity usersByUsersId) {
//        this.usersEntity = usersByUsersId;
//    }
//}
