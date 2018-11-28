//package org.upgrad.controllers;
//
//import com.google.common.base.Charsets;
//import com.google.common.hash.Hashing;
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
//import org.upgrad.models.User;
//import org.upgrad.models.UserAuthToken;
//import org.upgrad.services.UserAuthTokenService;
//import org.upgrad.services.UserService;
//
//import static org.assertj.core.util.DateUtil.now;
//import static org.hamcrest.Matchers.containsString;
//import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON;
//
//// This class contains all the test cases regarding the user controller
//@RunWith(SpringRunner.class)
//@WebMvcTest(UserController.class)
//public class UserControllerTest {
//
//    @Autowired
//    private MockMvc mvc;
//
//    @MockBean
//    private UserService userService;
//
//    @MockBean
//    private UserAuthTokenService userAuthTokenService;
//
//    @Test
//    public void registerUserWithAlreadyRegisteredContactNumber() throws Exception{
//        User user = new User();
//        user.setId(1);
//        user.setContactNumber("1234567890");
//        String firstName = "software";
//        String lastName = "development";
//        String email = "x@y.com";
//        String contactNumber = "1234567890";
//        String password = "Upgrad@123";
//        Mockito.when(userService.findUser(contactNumber)).thenReturn(user);
//        String url = "/user/signup";
//        mvc.perform(post(url)
//                .param("firstName", firstName)
//                .param("lastName", lastName)
//                .param("email", email)
//                .param("contactNumber", contactNumber)
//                .param("password", password))
//                .andExpect(status().is4xxClientError())
//                .andExpect(content().string(containsString("Try any other contact number, this contact number has already been registered!")));
//    }
//
//    @Test
//    public void registerUserWithFaultyEmail() throws Exception{
//        String firstName = "software";
//        String lastName = "development";
//        String email = "x.y@com";
//        String contactNumber = "1234567890";
//        String password = "Upgrad@123";
//        Mockito.when(userService.findUser(contactNumber)).thenReturn(null);
//        String url = "/user/signup";
//        mvc.perform(post(url)
//                .param("firstName", firstName)
//                .param("lastName", lastName)
//                .param("email", email)
//                .param("contactNumber", contactNumber)
//                .param("password", password))
//                .andExpect(status().is4xxClientError())
//                .andExpect(content().string(containsString("Invalid email-id format!")));
//    }
//
//    @Test
//    public void registerUserWithFaultyContactNumber() throws Exception{
//        String firstName = "software";
//        String lastName = "development";
//        String email = "x@y.com";
//        String contactNumber = "123456789y";
//        String password = "Upgrad@123";
//        Mockito.when(userService.findUser(contactNumber)).thenReturn(null);
//        String url = "/user/signup";
//        mvc.perform(post(url)
//                .param("firstName", firstName)
//                .param("lastName", lastName)
//                .param("email", email)
//                .param("contactNumber", contactNumber)
//                .param("password", password))
//                .andExpect(status().is4xxClientError())
//                .andExpect(content().string(containsString("Invalid contact number!")));
//    }
//
//    @Test
//    public void registerUserWithFaultyPassword() throws Exception{
//        String firstName = "software";
//        String lastName = "development";
//        String email = "x@y.com";
//        String contactNumber = "1234567890";
//        String password = "upgrad@123";
//        Mockito.when(userService.findUser(contactNumber)).thenReturn(null);
//        String url = "/user/signup";
//        mvc.perform(post(url)
//                .param("firstName", firstName)
//                .param("lastName", lastName)
//                .param("email", email)
//                .param("contactNumber", contactNumber)
//                .param("password", password))
//                .andExpect(status().is4xxClientError())
//                .andExpect(content().string(containsString("Weak password!")));
//    }
//
//    @Test
//    public void successfulSignup() throws Exception{
//        String firstName = "software";
//        String lastName = "development";
//        String email = "x@y.com";
//        String contactNumber = "1234567890";
//        String password = "Upgrad@123";
//        Mockito.when(userService.findUser(contactNumber)).thenReturn(null);
//        String url = "/user/signup";
//        mvc.perform(post(url)
//                .param("firstName", firstName)
//                .param("lastName", lastName)
//                .param("email", email)
//                .param("contactNumber", contactNumber)
//                .param("password", password))
//                .andExpect(status().is2xxSuccessful())
//                .andExpect(content().string(containsString("User with contact number 1234567890 successfully registered!")));
//    }
//
//    @Test
//    public void updateUserDetailsWithoutLogin() throws Exception{
//        String firstName = "software";
//        String lastName = "development";
//        String accessToken = "#############################";
//        Mockito.when(userAuthTokenService.isUserLoggedIn(accessToken)).thenReturn(null);
//        String url = "/user";
//        mvc.perform(put(url)
//                .param("firstName", firstName)
//                .param("lastName", lastName)
//                .header("accessToken", accessToken))
//                .andExpect(status().is4xxClientError())
//                .andExpect(content().string(containsString("Please Login first to access this endpoint!")));
//    }
//
//    @Test
//    public void updateUserDetailsWithLoggedOutUser() throws Exception{
//        String firstName = "software";
//        String lastName = "development";
//        String accessToken = "#############################";
//        UserAuthToken userAuthToken = new UserAuthToken();
//        userAuthToken.setLogoutAt(now());
//        Mockito.when(userAuthTokenService.isUserLoggedIn(accessToken)).thenReturn(userAuthToken);
//        String url = "/user";
//        mvc.perform(put(url)
//                .param("firstName", firstName)
//                .param("lastName", lastName)
//                .header("accessToken", accessToken))
//                .andExpect(status().is4xxClientError())
//                .andExpect(content().string(containsString("You have already logged out. Please Login first to access this endpoint!")));
//    }
//
//    @Test
//    public void updateUserDetails() throws Exception{
//        String firstName = "software";
//        String lastName = "development";
//        String accessToken = "#############################";
//        User user = new User();
//        user.setId(1);
//        user.setFirstName("soft");
//        Integer userId=1;
//        UserAuthToken userAuthToken = new UserAuthToken();
//        Mockito.when(userAuthTokenService.isUserLoggedIn(accessToken)).thenReturn(userAuthToken);
//        Mockito.when(userAuthTokenService.getUserId(accessToken)).thenReturn(1);
//        Mockito.when(userService.updateUser(firstName,lastName,userId)).thenReturn(user);
//        String url = "/user";
//        mvc.perform(put(url)
//                .contentType(MediaType.asMediaType(APPLICATION_JSON))
//                .param("firstName", firstName)
//                .param("lastName", lastName)
//                .header("accessToken", accessToken))
//                .andExpect(status().is2xxSuccessful())
//                .andExpect(jsonPath("$.firstName", Matchers.is("soft"))); }
//
//    @Test
//    public void updateUserPasswordWithoutLogin() throws Exception{
//        String oldPassword = "Upgrad@12345";
//        String newPassword = "Upgrad@123";
//        String accessToken = "#############################";
//        Mockito.when(userAuthTokenService.isUserLoggedIn(accessToken)).thenReturn(null);
//        String url = "/user/password";
//        mvc.perform(put(url)
//                .param("oldPassword", oldPassword)
//                .param("newPassword", newPassword)
//                .header("accessToken", accessToken))
//                .andExpect(status().is4xxClientError())
//                .andExpect(content().string(containsString("Please Login first to access this endpoint!")));
//    }
//
//    @Test
//    public void updateUserPasswordWithLoggedOutUser() throws Exception{
//        String oldPassword = "Upgrad@12345";
//        String newPassword = "Upgrad@123";
//        String accessToken = "#############################";
//        UserAuthToken userAuthToken = new UserAuthToken();
//        userAuthToken.setLogoutAt(now());
//        Mockito.when(userAuthTokenService.isUserLoggedIn(accessToken)).thenReturn(userAuthToken);
//        String url = "/user/password";
//        mvc.perform(put(url)
//                .param("oldPassword", oldPassword)
//                .param("newPassword", newPassword)
//                .header("accessToken", accessToken))
//                .andExpect(status().is4xxClientError())
//                .andExpect(content().string(containsString("You have already logged out. Please Login first to access this endpoint!")));
//    }
//
//    @Test
//    public void updateUserPasswordWithIncorrectOldPassword() throws Exception {
//        String oldPassword = "Upgrad@12345";
//        String newPassword = "Upgrad@123";
//        String accessToken = "#############################";
//        User user = new User();
//        user.setId(1);
//        user.setPassword("Upgrad@1234");
//        Integer userId = 1;
//        UserAuthToken userAuthToken = new UserAuthToken();
//        Mockito.when(userAuthTokenService.isUserLoggedIn(accessToken)).thenReturn(userAuthToken);
//        Mockito.when(userAuthTokenService.getUserId(accessToken)).thenReturn(1);
//        Mockito.when(userService.getUserById(userId)).thenReturn(user);
//        String url = "/user/password";
//        mvc.perform(put(url)
//                .param("oldPassword", oldPassword)
//                .param("newPassword", newPassword)
//                .header("accessToken", accessToken))
//                .andExpect(status().is4xxClientError())
//                .andExpect(content().string(containsString("Your password did not match to your old password!")));
//    }
//
//
//    @Test
//    public void updateUserPasswordWithWeakPassword() throws Exception {
//        String oldPassword = "Upgrad@12345";
//        String oldPasswordHash = Hashing.sha256()
//                .hashString(oldPassword, Charsets.US_ASCII)
//                .toString();
//        String newPassword = "Upgrad123";
//        String accessToken = "############################";
//        User user = new User();
//        user.setId(1);
//        user.setPassword(oldPasswordHash);
//        Integer userId = 1;
//        UserAuthToken userAuthToken = new UserAuthToken();
//        Mockito.when(userAuthTokenService.isUserLoggedIn(accessToken)).thenReturn(userAuthToken);
//        Mockito.when(userAuthTokenService.getUserId(accessToken)).thenReturn(1);
//        Mockito.when(userService.getUserById(userId)).thenReturn(user);
//        String url = "/user/password";
//        mvc.perform(put(url)
//                .param("oldPassword", oldPassword)
//                .param("newPassword", newPassword)
//                .header("accessToken", accessToken))
//                .andExpect(status().is4xxClientError())
//                .andExpect(content().string(containsString("Weak password!")));
//    }
//
//    @Test
//    public void updateUserPassword() throws Exception {
//        String oldPassword = "Upgrad@12345";
//        String oldPasswordHash = Hashing.sha256()
//                .hashString(oldPassword, Charsets.US_ASCII)
//                .toString();
//        String newPassword = "Upgrad@123";
//        String accessToken = "#############################";
//        User user = new User();
//        user.setId(1);
//        user.setPassword(oldPasswordHash);
//        Integer userId = 1;
//        UserAuthToken userAuthToken = new UserAuthToken();
//        Mockito.when(userAuthTokenService.isUserLoggedIn(accessToken)).thenReturn(userAuthToken);
//        Mockito.when(userAuthTokenService.getUserId(accessToken)).thenReturn(1);
//        Mockito.when(userService.getUserById(userId)).thenReturn(user);
//        String url = "/user/password";
//        mvc.perform(put(url)
//                .param("oldPassword", oldPassword)
//                .param("newPassword", newPassword)
//                .header("accessToken", accessToken))
//                .andExpect(status().is2xxSuccessful())
//                .andExpect(content().string(containsString("Password updated successfully!")));
//    }
//
//    }
