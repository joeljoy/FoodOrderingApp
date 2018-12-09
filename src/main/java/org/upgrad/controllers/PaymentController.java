package org.upgrad.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.upgrad.models.Payment;
import org.upgrad.models.User;
import org.upgrad.models.UserAuthToken;
import org.upgrad.services.PaymentService;
import org.upgrad.services.UserAuthTokenService;

import java.util.List;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private UserAuthTokenService userAuthTokenService;

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/")
    public ResponseEntity<?> getPaymentMethods(@RequestHeader String accessToken) {
        UserAuthToken userAuthToken = userAuthTokenService.isUserLoggedIn(accessToken);
        if (userAuthToken == null) {
            return new ResponseEntity<>("Please Login first to access this endpoint!", HttpStatus.UNAUTHORIZED);
        } else if (userAuthToken.getLogoutAt() != null) {
            return new ResponseEntity<>("You have already logged out. Please Login first to access this endpoint!", HttpStatus.UNAUTHORIZED);
        } else {
            List<Payment> paymentList = paymentService.getAll();
            return ResponseEntity.ok(paymentList);
        }
    }
}
