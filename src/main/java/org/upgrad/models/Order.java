package org.upgrad.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.security.DigestOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "bill")
    private Double bill;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Coupon coupon;

    @Column(name = "discount")
    private Double discount;

    @Column(name = "date")
    private Date date;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Payment payment;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private User user;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Address address;

    public Order() {
    }

    public Order(Double bill, Coupon coupon, Double discount, Date date, Payment payment, User user, Address address) {
        this.bill = bill;
        this.coupon = coupon;
        this.discount = discount;
        this.date = date;
        this.payment = payment;
        this.user = user;
        this.address = address;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getBill() {
        return bill;
    }

    public void setBill(Double bill) {
        this.bill = bill;
    }

    public Coupon getCoupon() {
        return coupon;
    }

    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
