package org.upgrad.models;

import javax.persistence.*;

@Entity
@Table(name = "coupon")
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "coupon_name", nullable = false)
    private String couponName;

    @Column(name = "percent")
    private Integer percent;

    // TODO: Check this!
//    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    private Order order;

    public Coupon() {}

    public Coupon(String couponName, Integer percent) {
        this.couponName = couponName;
        this.percent = percent;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public Integer getPercent() {
        return percent;
    }

    public void setPercent(Integer percent) {
        this.percent = percent;
    }

//    public Order getOrder() {
//        return order;
//    }

//    public void setOrder(Order order) {
//        this.order = order;
//    }
}
