//package org.upgrad.controllers;
//
//import org.hamcrest.Matchers;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.upgrad.models.Address;
//import org.upgrad.models.States;
//import org.upgrad.models.UserAuthToken;
//import org.upgrad.services.AddressService;
//import org.upgrad.services.UserAuthTokenService;
//
//import java.util.List;
//
//import static java.util.Collections.singletonList;
//import static org.assertj.core.util.DateUtil.now;
//import static org.hamcrest.Matchers.containsString;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON;
//
//// This class contains all the test cases regarding the address controller
//@RunWith(SpringRunner.class)
//@WebMvcTest(AddressController.class)
//public class AddressControllerTest {
//
//    @Autowired
//    private MockMvc mvc;
//
//    @MockBean
//    private AddressService addressService;
//
//    @MockBean
//    private UserAuthTokenService userAuthTokenService;
//
//    @Test
//    public void saveAddressWithoutLogin() throws Exception{
//        String flatBuilNo = "123/32 Nishuvi Building";
//        String locality = "Worli";
//        String city = "Mumbai";
//        String zipcode = "120018";
//        String type = "temp";
//        String stateId = "20";
//        String accessToken = "#############################";
//        Mockito.when(userAuthTokenService.isUserLoggedIn(accessToken)).thenReturn(null);
//        String url = "/address";
//        mvc.perform(post(url)
//                .param("flatBuilNo", flatBuilNo)
//                .param("locality", locality)
//                .param("city", city)
//                .param("zipcode", zipcode)
//                .param("type", type)
//                .param("stateId", stateId)
//                .header("accessToken", accessToken))
//                .andExpect(status().is4xxClientError())
//                .andExpect(content().string(containsString("Please Login first to access this endpoint!")));
//    }
//
//    @Test
//    public void saveAddressWithLoggedOutUser() throws Exception{
//        String flatBuilNo = "123/32 Nishuvi Building";
//        String locality = "Worli";
//        String city = "Mumbai";
//        String zipcode = "120018";
//        String type = "temp";
//        String stateId = "20";
//        String accessToken = "#############################";
//        UserAuthToken userAuthToken = new UserAuthToken();
//        userAuthToken.setLogoutAt(now());
//        Mockito.when(userAuthTokenService.isUserLoggedIn(accessToken)).thenReturn(userAuthToken);
//        String url = "/address";
//        mvc.perform(post(url)
//                .param("flatBuilNo", flatBuilNo)
//                .param("locality", locality)
//                .param("city", city)
//                .param("zipcode", zipcode)
//                .param("type", type)
//                .param("stateId", stateId)
//                .header("accessToken", accessToken))
//                .andExpect(status().is4xxClientError())
//                .andExpect(content().string(containsString("You have already logged out. Please Login first to access this endpoint!")));
//    }
//
//    @Test
//    public void saveAddressWithInvalidZipcode() throws Exception{
//        String flatBuilNo = "123/32 Nishuvi Building";
//        String locality = "Worli";
//        String city = "Mumbai";
//        String zipcode = "12001r";
//        String type = "temp";
//        String stateId = "20";
//        String accessToken = "#############################";
//        UserAuthToken userAuthToken = new UserAuthToken();
//        Mockito.when(userAuthTokenService.isUserLoggedIn(accessToken)).thenReturn(userAuthToken);
//        String url = "/address";
//        mvc.perform(post(url)
//                .param("flatBuilNo", flatBuilNo)
//                .param("locality", locality)
//                .param("city", city)
//                .param("zipcode", zipcode)
//                .param("type", type)
//                .param("stateId", stateId)
//                .header("accessToken", accessToken))
//                .andExpect(status().is4xxClientError())
//                .andExpect(content().string(containsString("Invalid zipcode!")));
//    }
//
//    @Test
//    public void saveAddress() throws Exception{
//        String flatBuilNo = "123/32 Nishuvi Building";
//        String locality = "Worli";
//        String city = "Mumbai";
//        String zipcode = "140018";
//        String type = "temp";
//        String stateId = "20";
//        String accessToken = "#############################";
//        UserAuthToken userAuthToken = new UserAuthToken();
//        Integer userId = 1;
//        Mockito.when(userAuthTokenService.isUserLoggedIn(accessToken)).thenReturn(userAuthToken);
//        Mockito.when(userAuthTokenService.getUserId(accessToken)).thenReturn(userId);
//        String url = "/address";
//        mvc.perform(post(url)
//                .param("flatBuilNo", flatBuilNo)
//                .param("locality", locality)
//                .param("city", city)
//                .param("zipcode", zipcode)
//                .param("type", type)
//                .param("stateId", stateId)
//                .header("accessToken", accessToken))
//                .andExpect(status().is2xxSuccessful())
//                .andExpect(content().string(containsString("Address has been saved successfully!")));
//    }
//
//    @Test
//    public void updateAddressWithoutLogin() throws Exception{
//        String flatBuilNo = "123/32 Nishuvi Building";
//        String locality = "Worli";
//        String city = "Mumbai";
//        String zipcode = "120018";
//        String type = "temp";
//        String stateId = "20";
//        String accessToken = "#############################";
//        Mockito.when(userAuthTokenService.isUserLoggedIn(accessToken)).thenReturn(null);
//        String url = "/address/1";
//        mvc.perform(put(url)
//                .param("flatBuilNo", flatBuilNo)
//                .param("locality", locality)
//                .param("city", city)
//                .param("zipcode", zipcode)
//                .param("type", type)
//                .param("stateId", stateId)
//                .header("accessToken", accessToken))
//                .andExpect(status().is4xxClientError())
//                .andExpect(content().string(containsString("Please Login first to access this endpoint!")));
//    }
//
//    @Test
//    public void updateAddressWithLoggedOutUser() throws Exception{
//        String flatBuilNo = "123/32 Nishuvi Building";
//        String locality = "Worli";
//        String city = "Mumbai";
//        String zipcode = "120018";
//        String type = "temp";
//        String stateId = "20";
//        String accessToken = "#############################";
//        UserAuthToken userAuthToken = new UserAuthToken();
//        userAuthToken.setLogoutAt(now());
//        Mockito.when(userAuthTokenService.isUserLoggedIn(accessToken)).thenReturn(userAuthToken);
//        String url = "/address/1";
//        mvc.perform(put(url)
//                .param("flatBuilNo", flatBuilNo)
//                .param("locality", locality)
//                .param("city", city)
//                .param("zipcode", zipcode)
//                .param("type", type)
//                .param("stateId", stateId)
//                .header("accessToken", accessToken))
//                .andExpect(status().is4xxClientError())
//                .andExpect(content().string(containsString("You have already logged out. Please Login first to access this endpoint!")));
//    }
//
//    @Test
//    public void updateAddressWithInvalidZipcode() throws Exception{
//        String flatBuilNo = "123/32 Nishuvi Building";
//        String locality = "Worli";
//        String city = "Mumbai";
//        String zipcode = "12001r";
//        String type = "temp";
//        String stateId = "20";
//        String accessToken = "#############################";
//        UserAuthToken userAuthToken = new UserAuthToken();
//        Mockito.when(userAuthTokenService.isUserLoggedIn(accessToken)).thenReturn(userAuthToken);
//        String url = "/address/1";
//        mvc.perform(put(url)
//                .param("flatBuilNo", flatBuilNo)
//                .param("locality", locality)
//                .param("city", city)
//                .param("zipcode", zipcode)
//                .param("type", type)
//                .param("stateId", stateId)
//                .header("accessToken", accessToken))
//                .andExpect(status().is4xxClientError())
//                .andExpect(content().string(containsString("Invalid zipcode!")));
//    }
//
//    @Test
//    public void updateAddressWithInvalifAddressId() throws Exception{
//        String flatBuilNo = "123/32 Nishuvi Building";
//        String locality = "Worli";
//        String city = "Mumbai";
//        String zipcode = "140018";
//        String type = "temp";
//        String stateId = "20";
//        String accessToken = "#############################";
//        UserAuthToken userAuthToken = new UserAuthToken();
//        Mockito.when(userAuthTokenService.isUserLoggedIn(accessToken)).thenReturn(userAuthToken);
//        Mockito.when(addressService.getAddress(2)).thenReturn(Boolean.FALSE);
//        String url = "/address/2";
//        mvc.perform(put(url)
//                .param("flatBuilNo", flatBuilNo)
//                .param("locality", locality)
//                .param("city", city)
//                .param("zipcode", zipcode)
//                .param("type", type)
//                .param("stateId", stateId)
//                .header("accessToken", accessToken))
//                .andExpect(status().is4xxClientError())
//                .andExpect(content().string(containsString("No address with this address id!")));
//    }
//
//    @Test
//    public void updateAddress() throws Exception{
//        String flatBuilNo = "123/32 Nishuvi Building";
//        String locality = "Worli";
//        String city = "Mumbai";
//        String zipcode = "140018";
//        String type = "temp";
//        String stateId = "20";
//        String accessToken = "#############################";
//        UserAuthToken userAuthToken = new UserAuthToken();
//        Mockito.when(userAuthTokenService.isUserLoggedIn(accessToken)).thenReturn(userAuthToken);
//        Mockito.when(addressService.getAddress(2)).thenReturn(Boolean.TRUE);
//        String url = "/address/2";
//        mvc.perform(put(url)
//                .param("flatBuilNo", flatBuilNo)
//                .param("locality", locality)
//                .param("city", city)
//                .param("zipcode", zipcode)
//                .param("type", type)
//                .param("stateId", stateId)
//                .header("accessToken", accessToken))
//                .andExpect(status().is2xxSuccessful())
//                .andExpect(content().string(containsString("Address has been updated successfully!")));
//    }
//
//    @Test
//    public void getAddressesWithoutLogin() throws Exception{
//        String accessToken = "#############################";
//        Mockito.when(userAuthTokenService.isUserLoggedIn(accessToken)).thenReturn(null);
//        String url = "/address/user";
//        mvc.perform(get(url)
//                .header("accessToken", accessToken))
//                .andExpect(status().is4xxClientError())
//                .andExpect(content().string(containsString("Please Login first to access this endpoint!")));
//    }
//
//    @Test
//    public void getAddressesWithLoggedOutUser() throws Exception{
//        String accessToken = "#############################";
//        UserAuthToken userAuthToken = new UserAuthToken();
//        userAuthToken.setLogoutAt(now());
//        Mockito.when(userAuthTokenService.isUserLoggedIn(accessToken)).thenReturn(userAuthToken);
//        String url = "/address/user";
//        mvc.perform(get(url)
//                .header("accessToken", accessToken))
//                .andExpect(status().is4xxClientError())
//                .andExpect(content().string(containsString("You have already logged out. Please Login first to access this endpoint!")));
//    }
//
//    @Test
//    public void getAddressesWithNoSavedAddress() throws Exception{
//        String accessToken = "#############################";
//        UserAuthToken userAuthToken = new UserAuthToken();
//        Integer userId = 1;
//        Mockito.when(userAuthTokenService.isUserLoggedIn(accessToken)).thenReturn(userAuthToken);
//        Mockito.when(userAuthTokenService.getUserId(accessToken)).thenReturn(userId);
//        Mockito.when(addressService.getPermAddress(userId)).thenReturn(null);
//        String url = "/address/user";
//        mvc.perform(get(url)
//                .header("accessToken", accessToken))
//                .andExpect(status().is4xxClientError())
//                .andExpect(content().string(containsString("No permanent address found!")));
//    }
//
//    @Test
//    public void getAddressesByUser() throws Exception{
//        String accessToken = "#############################";
//        UserAuthToken userAuthToken = new UserAuthToken();
//        Integer userId = 1;
//        Address address = new Address();
//        address.setId(1);
//        address.setCity("Mumbai");
//        List<Address> addresses = singletonList(address);
//        Mockito.when(userAuthTokenService.isUserLoggedIn(accessToken)).thenReturn(userAuthToken);
//        Mockito.when(userAuthTokenService.getUserId(accessToken)).thenReturn(userId);
//        Mockito.when(addressService.getPermAddress(userId)).thenReturn(addresses);
//        String url = "/address/user";
//        mvc.perform(get(url)
//                .contentType(MediaType.asMediaType(APPLICATION_JSON))
//                .header("accessToken", accessToken))
//                .andExpect(status().is2xxSuccessful())
//                .andExpect(jsonPath("$[0].city", Matchers.is("Mumbai")));   }
//
//
//
//    @Test
//    public void deleteAddressWithoutLogin() throws Exception{
//        String accessToken = "#############################";
//        Mockito.when(userAuthTokenService.isUserLoggedIn(accessToken)).thenReturn(null);
//        String url = "/address/1";
//        mvc.perform(delete(url)
//                .header("accessToken", accessToken))
//                .andExpect(status().is4xxClientError())
//                .andExpect(content().string(containsString("Please Login first to access this endpoint!")));
//    }
//
//    @Test
//    public void deleteAddressWithLoggedOutUser() throws Exception{
//        String accessToken = "#############################";
//        UserAuthToken userAuthToken = new UserAuthToken();
//        userAuthToken.setLogoutAt(now());
//        Mockito.when(userAuthTokenService.isUserLoggedIn(accessToken)).thenReturn(userAuthToken);
//        String url = "/address/1";
//        mvc.perform(delete(url)
//                .header("accessToken", accessToken))
//                .andExpect(status().is4xxClientError())
//                .andExpect(content().string(containsString("You have already logged out. Please Login first to access this endpoint!")));
//    }
//
//
//    @Test
//    public void deleteAddressWithInvalidAddressId() throws Exception{
//        String accessToken = "#############################";
//        UserAuthToken userAuthToken = new UserAuthToken();
//        Mockito.when(userAuthTokenService.isUserLoggedIn(accessToken)).thenReturn(userAuthToken);
//        Mockito.when(addressService.getAddress(2)).thenReturn(Boolean.FALSE);
//        String url = "/address/2";
//        mvc.perform(delete(url)
//                .header("accessToken", accessToken))
//                .andExpect(status().is4xxClientError())
//                .andExpect(content().string(containsString("No address with this address id!")));
//    }
//
//    @Test
//    public void deleteAddress() throws Exception{
//        String accessToken = "#############################";
//        UserAuthToken userAuthToken = new UserAuthToken();
//        Mockito.when(userAuthTokenService.isUserLoggedIn(accessToken)).thenReturn(userAuthToken);
//        Mockito.when(addressService.getAddress(2)).thenReturn(Boolean.TRUE);
//        String url = "/address/2";
//        mvc.perform(delete(url)
//                .header("accessToken", accessToken))
//                .andExpect(status().is2xxSuccessful())
//                .andExpect(content().string(containsString("Address has been deleted successfully!")));
//    }
//
//    @Test
//    public void getAllStates() throws Exception{
//        States states = new States();
//        states.setId(1);
//        states.setStateName("Maharashtra");
//        List<States> statesList = singletonList(states);
//        Mockito.when(addressService.getAllStates()).thenReturn(statesList);
//        String url = "/states";
//        mvc.perform(get(url)
//                .contentType(MediaType.asMediaType(APPLICATION_JSON)))
//                .andExpect(status().is2xxSuccessful())
//                .andExpect(jsonPath("$[0].stateName", Matchers.is("Maharashtra")));
//    }
//
//
//
//}
