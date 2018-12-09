package org.upgrad.services;

import org.upgrad.requestResponseEntity.CouponResponse;

import java.util.List;

public interface CouponService {
    List<CouponResponse> getCouponByName(String couponName);
}
