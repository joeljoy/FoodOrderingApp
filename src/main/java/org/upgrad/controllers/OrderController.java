package org.upgrad.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.upgrad.models.*;
import org.upgrad.repositories.OrderItemRepository;
import org.upgrad.repositories.StateRepository;
import org.upgrad.requestResponseEntity.ItemQuantity;
import org.upgrad.services.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CouponService couponService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private OrderItemRepository orderItemRepository;

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

    @PostMapping("/")
    public ResponseEntity<?> addOrder(@RequestParam Integer addressId,
                                      @RequestParam(required = false) String flatBuildNo,
                                      @RequestParam(required = false) String locality,
                                      @RequestParam(required = false) String city,
                                      @RequestParam(required = false) String zipcode,
                                      @RequestParam(required = false) Integer stateId,
                                      @RequestParam(required = false) String type,
                                      @RequestParam Integer paymentId,
                                      @RequestBody List<ItemQuantity> itemQuantities,
                                      @RequestParam Double bill,
                                      @RequestParam(required = false) Integer couponId,
                                      @RequestParam(required = false) Double discount,
                                      @RequestHeader String accessToken) {
        UserAuthToken userAuthToken = userAuthTokenService.isUserLoggedIn(accessToken);
        if (userAuthToken == null) {
            return new ResponseEntity<>("Please Login first to access this endpoint!", HttpStatus.UNAUTHORIZED);
        } else if (userAuthToken.getLogoutAt() != null) {
            return new ResponseEntity<>("You have already logged out. Please Login first to access this endpoint!", HttpStatus.UNAUTHORIZED);
        } else {

            User user = userAuthToken.getUser();

            if (zipcode != null && zipcode.length() != 6) {
                return new ResponseEntity<>("Invalid zip code!", HttpStatus.BAD_REQUEST);
            }

            Address address = addressService.getAddressById(addressId);

            if (address == null) {
                address.setId(addressId);
                address.setFlatbuildNumber(flatBuildNo);
                address.setLocality(locality);
                address.setCity(city);
                address.setZipcode(zipcode);

                State state = stateRepository.getById(stateId);
                address.setState(state);
            }

            List<User> userList = new ArrayList<>(Arrays.asList(user));
            List<Address> addressList = new ArrayList<>(Arrays.asList(address));
            UserAddress userAddress = new UserAddress(type, userList, addressList);

            Payment payment = paymentService.getById(paymentId);

            if (discount == null) {
                discount = 0.0;
            }

            Coupon coupon = couponService.getById(couponId);
            Date date = new Date();
            Order order = new Order(bill, coupon, discount, date, payment, user, address);

            List<OrderItem> orderItems = new ArrayList<>();
            for (ItemQuantity itemQuantity: itemQuantities) {
                Item item = itemService.getById(itemQuantity.getItemId());
                OrderItem orderItem = new OrderItem(itemQuantity.getQuantity(), item.getPrice(), order, item);
                orderItemRepository.save(orderItem);
            }

            return new ResponseEntity<>(orderService.setOrder(order), HttpStatus.CREATED);
        }
    }
}
