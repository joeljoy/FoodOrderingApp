//package org.upgrad.controllers;
//
//import org.hamcrest.Matchers;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.upgrad.models.Coupon;
//import org.upgrad.models.Order;
//import org.upgrad.requestResponseEntity.ItemQuantity;
//import org.upgrad.models.UserAuthToken;
//import org.upgrad.services.OrderService;
//import org.upgrad.services.UserAuthTokenService;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//
//import static java.util.Collections.singletonList;
//import static org.assertj.core.util.DateUtil.now;
//import static org.hamcrest.Matchers.comparesEqualTo;
//import static org.hamcrest.Matchers.containsString;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//// This class contains all the test cases regarding the order controller
//@RunWith(SpringRunner.class)
//@WebMvcTest(OrderController.class)
//public class OrderControllerTest {
//
//    @Autowired
//    private MockMvc mvc;
//
//    @MockBean
//    private OrderService orderService;
//
//    @MockBean
//    private UserAuthTokenService userAuthTokenService;
//
//    @Test
//    public void getCouponWithoutLogin() throws Exception{
//        String accessToken = "#############################";
//        Mockito.when(userAuthTokenService.isUserLoggedIn(accessToken)).thenReturn(null);
//        String url = "/order/coupon/FLAT50";
//        mvc.perform(get(url)
//                .header("accessToken", accessToken))
//                .andExpect(status().is4xxClientError())
//                .andExpect(content().string(containsString("Please Login first to access this endpoint!")));
//    }
//
//    @Test
//    public void getCouponWithLoggedOutUser() throws Exception{
//        String accessToken = "#############################";
//        UserAuthToken userAuthToken = new UserAuthToken();
//        userAuthToken.setLogoutAt(now());
//        Mockito.when(userAuthTokenService.isUserLoggedIn(accessToken)).thenReturn(userAuthToken);
//        String url = "/order/coupon/FLAT50";
//        mvc.perform(get(url)
//                .header("accessToken", accessToken))
//                .andExpect(status().is4xxClientError())
//                .andExpect(content().string(containsString("You have already logged out. Please Login first to access this endpoint!")));
//    }
//
//
//    @Test
//    public void getCouponWithInvalidCouponName() throws Exception{
//        String accessToken = "#############################";
//        UserAuthToken userAuthToken = new UserAuthToken();
//        Mockito.when(userAuthTokenService.isUserLoggedIn(accessToken)).thenReturn(userAuthToken);
//        Mockito.when(orderService.getCoupon("FLAT100")).thenReturn(null);
//        String url = "/order/coupon/FLAT100";
//        mvc.perform(get(url)
//                .header("accessToken", accessToken))
//                .andExpect(status().is4xxClientError())
//                .andExpect(content().string(containsString("Invalid Coupon!")));
//    }
//
//    @Test
//    public void getCoupon() throws Exception{
//        String accessToken = "#############################";
//        UserAuthToken userAuthToken = new UserAuthToken();
//        Coupon coupon = new Coupon();
//        coupon.setCouponName("FLAT50");
//        coupon.setPercent(50);
//        Mockito.when(userAuthTokenService.isUserLoggedIn(accessToken)).thenReturn(userAuthToken);
//        Mockito.when(orderService.getCoupon("FLAT50")).thenReturn(coupon);
//        String url = "/order/coupon/FLAT50";
//        mvc.perform(get(url)
//                .header("accessToken", accessToken))
//                .andExpect(status().is2xxSuccessful())
//                .andExpect(jsonPath("$.percent", Matchers.is(50)))
//                .andExpect(jsonPath("$.couponName", Matchers.is("FLAT50")));
//    }
//
//    @Test
//    public void getOrdersWithoutLogin() throws Exception{
//        String accessToken = "#############################";
//        Mockito.when(userAuthTokenService.isUserLoggedIn(accessToken)).thenReturn(null);
//        String url = "/order";
//        mvc.perform(get(url)
//                .header("accessToken", accessToken))
//                .andExpect(status().is4xxClientError())
//                .andExpect(content().string(containsString("Please Login first to access this endpoint!")));
//    }
//
//    @Test
//    public void getOrdersWithLoggedOutUser() throws Exception{
//        String accessToken = "#############################";
//        UserAuthToken userAuthToken = new UserAuthToken();
//        userAuthToken.setLogoutAt(now());
//        Mockito.when(userAuthTokenService.isUserLoggedIn(accessToken)).thenReturn(userAuthToken);
//        String url = "/order";
//        mvc.perform(get(url)
//                .header("accessToken", accessToken))
//                .andExpect(status().is4xxClientError())
//                .andExpect(content().string(containsString("You have already logged out. Please Login first to access this endpoint!")));
//    }
//
//
//    @Test
//    public void getOrdersWithNoPastOrders() throws Exception{
//        String accessToken = "#############################";
//        UserAuthToken userAuthToken = new UserAuthToken();
//        Integer userId = 1;
//        Mockito.when(userAuthTokenService.isUserLoggedIn(accessToken)).thenReturn(userAuthToken);
//        Mockito.when(userAuthTokenService.getUserId(accessToken)).thenReturn(userId);
//        Mockito.when(orderService.getOrdersByUser(userId)).thenReturn(null);
//        String url = "/order";
//        mvc.perform(get(url)
//                .header("accessToken", accessToken))
//                .andExpect(status().is4xxClientError())
//                .andExpect(content().string(containsString("No orders have been made yet!")));
//    }
//
//    @Test
//    public void getOrdersByUser() throws Exception{
//        String accessToken = "#############################";
//        UserAuthToken userAuthToken = new UserAuthToken();
//        Order order = new Order();
//        order.setBill(1200.0);
//        List<Order> orders = singletonList(order);
//        Integer userId = 1;
//        Mockito.when(userAuthTokenService.isUserLoggedIn(accessToken)).thenReturn(userAuthToken);
//        Mockito.when(userAuthTokenService.getUserId(accessToken)).thenReturn(userId);
//        Mockito.when(orderService.getOrdersByUser(userId)).thenReturn(orders);
//        String url = "/order";
//        mvc.perform(get(url)
//                .header("accessToken", accessToken))
//                .andExpect(status().is2xxSuccessful())
//                .andExpect(jsonPath("$[0].bill", Matchers.is(1200.0)));
//    }
//
//
//    @Test
//    public void saveOrdersWithoutLogin() throws Exception{
//        String addressId = "1";
//        String paymentId = "2";
//        String bill = "1200.0";
//        String discount = "0.0";
//        String accessToken = "#############################";
//        Mockito.when(userAuthTokenService.isUserLoggedIn(accessToken)).thenReturn(null);
//        String url = "/order";
//        mvc.perform(post(url)
//                .contentType(MediaType.APPLICATION_JSON)
//                .param("addressId",addressId)
//                .param("paymentId",paymentId)
//                .content(createUserInJson(1,2))
//                .param("bill",bill)
//                .param("discount",discount)
//                .header("accessToken", accessToken))
//                .andExpect(status().is4xxClientError())
//                .andExpect(content().string(containsString("Please Login first to access this endpoint!")));
//    }
//
//    private static String createUserInJson (Integer itemId, Integer quantity) {
//        return "[\n" +
//                "  {\n" +
//                "    \"itemId\":\"" + itemId + "\",\n" +
//                "    \"quantity\":\"" + quantity + "\" \n" +
//                "  }\n" +
//                "]";
//    }
//
//    @Test
//    public void saveOrdersWithLoggedOutUser() throws Exception{
//        String addressId = "1";
//        String paymentId = "2";
//        String bill = "1200.0";
//        String discount = "0.0";
//        String accessToken = "#############################";
//        UserAuthToken userAuthToken = new UserAuthToken();
//        userAuthToken.setLogoutAt(now());
//        Mockito.when(userAuthTokenService.isUserLoggedIn(accessToken)).thenReturn(userAuthToken);
//        String url = "/order";
//        mvc.perform(post(url)
//                .contentType(MediaType.APPLICATION_JSON)
//                .param("addressId",addressId)
//                .param("paymentId",paymentId)
//                .content(createUserInJson(1,2))
//                .param("bill",bill)
//                .param("discount",discount)
//                .header("accessToken", accessToken))
//                .andExpect(status().is4xxClientError())
//                .andExpect(content().string(containsString("You have already logged out. Please Login first to access this endpoint!")));
//    }
//
//
//    @Test
//    public void saveOrderWithInvalidZipcode() throws Exception{
//        String addressId = null;
//        String flatBuilNo = "123/32 Nishuvi Building";
//        String locality = "Worli";
//        String city = "Mumbai";
//        String zipcode = "12018";
//        String type = "temp";
//        String stateId = "20";
//        String paymentId = "2";
//        String bill = "1200.0";
//        String discount = "0.0";
//        String accessToken = "#############################";
//        UserAuthToken userAuthToken = new UserAuthToken();
//        Integer userId = 1;
//        Mockito.when(userAuthTokenService.isUserLoggedIn(accessToken)).thenReturn(userAuthToken);
//        Mockito.when(userAuthTokenService.getUserId(accessToken)).thenReturn(userId);
//        String url = "/order";
//        mvc.perform(post(url)
//                .contentType(MediaType.APPLICATION_JSON)
//                .param("addressId",addressId)
//                .param("flatBuilNo", flatBuilNo)
//                .param("locality", locality)
//                .param("city", city)
//                .param("zipcode", zipcode)
//                .param("type", type)
//                .param("stateId", stateId)
//                .param("paymentId",paymentId)
//                .content(createUserInJson(1,2))
//                .param("bill",bill)
//                .param("discount",discount)
//                .header("accessToken", accessToken))
//                .andExpect(status().is4xxClientError())
//                .andExpect(content().string(containsString("Invalid zipcode!")));
//    }
//
//    @Test
//    public void saveOrderWithValidZipcode() throws Exception{
//        String addressId = null;
//        String flatBuilNo = "123/32 Nishuvi Building";
//        String locality = "Worli";
//        String city = "Mumbai";
//        String zipcode = "120018";
//        String type = "temp";
//        String stateId = "20";
//        String paymentId = "2";
//        String bill = "1200.0";
//        String discount = "600.0";
//        Integer couponId = 1;
//        String accessToken = "#############################";
//        UserAuthToken userAuthToken = new UserAuthToken();
//        Integer userId = 1;
//        ItemQuantity itemQuantity = new ItemQuantity(1,1);
//        ItemQuantity itemQuantity1 = new ItemQuantity(2,2);
//        ArrayList<ItemQuantity> itemQuantities = new ArrayList<>();
//        itemQuantities.add(itemQuantity);
//        itemQuantities.add(itemQuantity1);
//        Mockito.when(userAuthTokenService.isUserLoggedIn(accessToken)).thenReturn(userAuthToken);
//        Mockito.when(userAuthTokenService.getUserId(accessToken)).thenReturn(userId);
//       // Mockito.when(orderService.addOrder(flatBuilNo, locality, city, zipcode, 2, type, 2, userId, itemQuantities, 1200.0,couponId,600.0)).thenReturn(7);
//        String url = "/order";
//        mvc.perform(post(url)
//                .contentType(MediaType.APPLICATION_JSON)
//                .param("addressId", addressId)
//                .param("flatBuilNo", flatBuilNo)
//                .param("locality", locality)
//                .param("city", city)
//                .param("zipcode", zipcode)
//                .param("type", type)
//                .param("stateId", stateId)
//                .param("paymentId",paymentId)
//                .content(createUserInJson(1,2))
//                .param("bill",bill)
//                .param("discount",discount)
//                .header("accessToken", accessToken))
//                .andExpect(status().is2xxSuccessful());
//
//    }
//
//    @Test
//    public void saveOrder() throws Exception{
//        String addressId = "1";
//        String paymentId = "2";
//        String bill = "1200.0";
//        String discount = "600.0";
//        String accessToken = "#############################";
//        UserAuthToken userAuthToken = new UserAuthToken();
//        ItemQuantity itemQuantity = new ItemQuantity(1,1);
//        ItemQuantity itemQuantity1 = new ItemQuantity(2,2);
//        ArrayList<ItemQuantity> itemQuantities = new ArrayList<>();
//        itemQuantities.add(itemQuantity);
//        itemQuantities.add(itemQuantity1);
//        Integer userId = 1;
//        Integer couponId = 1;
//        Integer orderId = 9;
//        Mockito.when(userAuthTokenService.isUserLoggedIn(accessToken)).thenReturn(userAuthToken);
//        Mockito.when(userAuthTokenService.getUserId(accessToken)).thenReturn(userId);
//        Mockito.when(orderService.addOrderWithPermAddress(1,2,userId,itemQuantities,1200.0,couponId,600.0)).thenReturn(orderId);
//        String url = "/order";
//        mvc.perform(post(url)
//                .contentType(MediaType.APPLICATION_JSON)
//                .param("addressId",addressId)
//                .param("paymentId",paymentId)
//                .content(createUserInJson(1,2))
//                .param("bill",bill)
//                .param("discount",discount)
//                .header("accessToken", accessToken))
//                .andExpect(status().is2xxSuccessful());
//
//    }
//
//
//
//}
