package org.upgrad.services;

import org.springframework.stereotype.Service;
import org.upgrad.models.Coupon;
import org.upgrad.repositories.CouponRepository;
import org.upgrad.requestResponseEntity.CouponResponse;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CouponServiceImpl implements CouponService {

    private CouponRepository couponRepository;

    public CouponServiceImpl(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    @Override
    public List<CouponResponse> getCouponByName(String couponName) {
        return getCouponResponse(couponRepository.findByName(couponName));
    }

    private List<CouponResponse> getCouponResponse(List<Coupon> coupons) {
        List<CouponResponse> couponResponseList = new ArrayList<>();
        for (Coupon coupon : coupons) {
            CouponResponse response = new CouponResponse(
                    coupon.getId(),
                    coupon.getCouponName(),
                    coupon.getPercent()
            );
            couponResponseList.add(response);
        }
        return couponResponseList;
    }
}
