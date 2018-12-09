package org.upgrad.requestResponseEntity;

import org.upgrad.models.*;

import java.util.Date;
import java.util.List;

public class OrderResponse {

    private Integer id;
    private Integer bill;
    private Coupon coupon;
    private Integer discount;
    private Date date;
    private Payment payment;
    private User user;
    private Address address;
    private List<OrderItem> orderItems;

    public OrderResponse() {
    }

    public OrderResponse(Integer id, Integer bill, Coupon coupon, Integer discount, Date date, Payment payment, User user, Address address, List<OrderItem> orderItems) {
        this.id = id;
        this.bill = bill;
        this.coupon = coupon;
        this.discount = discount;
        this.date = date;
        this.payment = payment;
        this.user = user;
        this.address = address;
        this.orderItems = orderItems;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBill() {
        return bill;
    }

    public void setBill(Integer bill) {
        this.bill = bill;
    }

    public Coupon getCoupon() {
        return coupon;
    }

    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
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

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}
