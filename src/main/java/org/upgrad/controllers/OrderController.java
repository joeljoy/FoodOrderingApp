package org.upgrad.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.upgrad.models.User;
import org.upgrad.models.UserAuthToken;
import org.upgrad.services.CouponService;
import org.upgrad.services.OrderService;
import org.upgrad.services.UserAuthTokenService;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CouponService couponService;

    @Autowired
    private UserAuthTokenService userAuthTokenService;

    @GetMapping("/coupon/{couponName}")
    public ResponseEntity<?> getCouponByName(@PathVariable("couponName") String couponName, @RequestHeader String accessToken) {
        if (userAuthTokenService.isUserLoggedIn(accessToken) == null) {
            return new ResponseEntity<>("Please Login first to access this endpoint!", HttpStatus.UNAUTHORIZED);
        } else if (userAuthTokenService.isUserLoggedIn(accessToken).getLogoutAt() != null) {
            return new ResponseEntity<>("You have already logged out. Please Login first to access this endpoint!", HttpStatus.UNAUTHORIZED);
        } else {
            List couponList = couponService.getCouponByName(couponName);
            if (couponList == null || couponList.isEmpty()) {
                return new ResponseEntity<>("Invalid Coupon!", HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.ok(couponList);
        }
    }

    @GetMapping("/")
    public ResponseEntity<?> getPastOrders(@RequestHeader String accessToken) {
        UserAuthToken userAuthToken = userAuthTokenService.isUserLoggedIn(accessToken);
        if (userAuthToken == null) {
            return new ResponseEntity<>("Please Login first to access this endpoint!", HttpStatus.UNAUTHORIZED);
        } else if (userAuthToken.getLogoutAt() != null) {
            return new ResponseEntity<>("You have already logged out. Please Login first to access this endpoint!", HttpStatus.UNAUTHORIZED);
        } else {
            User user = userAuthToken.getUser();
            List orderList = orderService.getPastOrders(user.getId());
            if (orderList == null || orderList.isEmpty()) {
                return new ResponseEntity<>("No orders have been made yet!", HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.ok(orderList);
        }
    }
}
