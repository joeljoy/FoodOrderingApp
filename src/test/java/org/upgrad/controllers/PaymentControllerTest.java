//package org.upgrad.controllers;
//
//import org.hamcrest.Matchers;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.upgrad.models.Payment;
//import org.upgrad.models.UserAuthToken;
//import org.upgrad.services.PaymentService;
//import org.upgrad.services.UserAuthTokenService;
//
//import java.util.List;
//
//import static java.util.Collections.singletonList;
//import static org.assertj.core.util.DateUtil.now;
//import static org.hamcrest.Matchers.containsString;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//// This class contains all the test cases regarding the payment controller
//@RunWith(SpringRunner.class)
//@WebMvcTest(PaymentController.class)
//public class PaymentControllerTest {
//
//    @Autowired
//    private MockMvc mvc;
//
//    @MockBean
//    private PaymentService paymentService;
//
//    @MockBean
//    private UserAuthTokenService userAuthTokenService;
//
//    @Test
//    public void getPaymentMethodsWithoutLogin() throws Exception{
//        String accessToken = "#############################";
//        Mockito.when(userAuthTokenService.isUserLoggedIn(accessToken)).thenReturn(null);
//        String url = "/payment";
//        mvc.perform(get(url)
//                .header("accessToken", accessToken))
//                .andExpect(status().is4xxClientError())
//                .andExpect(content().string(containsString("Please Login first to access this endpoint!")));
//    }
//
//    @Test
//    public void getPaymentMethodsWithLoggedOutUser() throws Exception{
//        String accessToken = "#############################";
//        UserAuthToken userAuthToken = new UserAuthToken();
//        userAuthToken.setLogoutAt(now());
//        Mockito.when(userAuthTokenService.isUserLoggedIn(accessToken)).thenReturn(userAuthToken);
//        String url = "/payment";
//        mvc.perform(get(url)
//                .header("accessToken", accessToken))
//                .andExpect(status().is4xxClientError())
//                .andExpect(content().string(containsString("You have already logged out. Please Login first to access this endpoint!")));
//    }
//
//    @Test
//    public void getPaymentMethods() throws Exception{
//        String accessToken = "#############################";
//        UserAuthToken userAuthToken = new UserAuthToken();
//        Payment payment = new Payment();
//        payment.setPaymentName("Net Banking");
//        List<Payment> payments = singletonList(payment);
//        Mockito.when(userAuthTokenService.isUserLoggedIn(accessToken)).thenReturn(userAuthToken);
//        Mockito.when(paymentService.getPaymentMethods()).thenReturn(payments);
//        String url = "/payment";
//        mvc.perform(get(url)
//                .header("accessToken", accessToken))
//                .andExpect(status().is2xxSuccessful())
//                .andExpect(jsonPath("$[0].paymentName", Matchers.is("Net Banking")));
//    }
//
//}
