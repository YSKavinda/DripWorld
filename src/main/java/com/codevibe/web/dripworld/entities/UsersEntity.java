package com.codevibe.web.dripworld.entities;

import com.codevibe.web.dripworld.constants.Role;
import com.codevibe.web.dripworld.constants.UserStatus;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users", schema = "dripwrld_db", catalog = "")
public class UsersEntity extends BaseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    public String getFirstName() {
        return firstName;
    }



    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "contact")
    private String contact;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    private UserStatus status;

    @Column(name = "email_verified_at")
    private Date emailVerifiedAt;

    @Column(name = "verification_code")
    private String verificationCode;

    @OneToMany(mappedBy = "usersByUsersId")
    private List<CartEntity> cartsById;
    @OneToMany(mappedBy = "usersByUsersId")
    private Collection<OrdersEntity> ordersById;

//    @OneToOne(mappedBy = "usersEntity")
//    private VendorsEntity vendorsEntity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public List<CartEntity> getCartsById() {
        return cartsById;
    }

    public void setCartsById(List<CartEntity> cartsById) {
        this.cartsById = cartsById;
    }

    public Collection<OrdersEntity> getOrdersById() {
        return ordersById;
    }

    public void setOrdersById(Collection<OrdersEntity> ordersById) {
        this.ordersById = ordersById;
    }


//    public VendorsEntity getVendor() {
//        return vendorsEntity;
//    }
//
//    public void setVendor(VendorsEntity vendorsEntity) {
//        this.vendorsEntity = vendorsEntity;
//    }

    public Date getEmailVerifiedAt() {
        return emailVerifiedAt;
    }

    public void setEmailVerifiedAt(Date emailVerifiedAt) {
        this.emailVerifiedAt = emailVerifiedAt;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }
}
