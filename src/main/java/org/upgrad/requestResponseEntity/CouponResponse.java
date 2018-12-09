package org.upgrad.requestResponseEntity;

public class CouponResponse {

    private Integer id;

    private String couponName;

    private Integer percent;

    public CouponResponse() {
    }

    public CouponResponse(Integer id, String couponName, Integer percent) {
        this.id = id;
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
}
