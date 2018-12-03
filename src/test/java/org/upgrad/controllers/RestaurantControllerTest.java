package org.upgrad.controllers;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.upgrad.models.Restaurant;
import org.upgrad.models.UserAuthToken;
import org.upgrad.requestResponseEntity.RestaurantResponse;
import org.upgrad.requestResponseEntity.RestaurantResponseCategorySet;
import org.upgrad.services.RestaurantService;
import org.upgrad.services.UserAuthTokenService;

import java.util.List;

import static java.util.Collections.singletonList;
import static org.assertj.core.util.DateUtil.now;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON;

// This class contains all the test cases regarding the restaurant controller
@RunWith(SpringRunner.class)
@WebMvcTest(RestaurantController.class)
public class RestaurantControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    RestaurantService restaurantService;

    @MockBean
    private UserAuthTokenService userAuthTokenService;

    @Test
    public void getAllRestaurants() throws Exception{
        RestaurantResponse restaurant = new RestaurantResponse();
        restaurant.setId(1);
        restaurant.setUserRating(4.3);
        List<RestaurantResponse> restaurants = singletonList(restaurant);
        Mockito.when(restaurantService.getAllRestaurant()).thenReturn(restaurants);
        String url = "/restaurant";
        mvc.perform(get(url)
                .contentType(MediaType.asMediaType(APPLICATION_JSON)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$[0].userRating", Matchers.is(4.3)));
    }
    @Test
    public void getRestaurantsByNameWithIncorrectName() throws Exception{
        Mockito.when(restaurantService.getRestaurantByName("kcf")).thenReturn(null);
        String url = "/restaurant/name/kcf";
        mvc.perform(get(url))
                .andExpect(status().is4xxClientError())
                .andExpect(content().string(containsString("No Restaurant by this name!")));
    }

    @Test
    public void getRestaurantsByName() throws Exception{
        RestaurantResponse restaurant = new RestaurantResponse();
        restaurant.setId(1);
        restaurant.setRestaurantName("dominoz");
        List<RestaurantResponse> restaurants = singletonList(restaurant);
        Mockito.when(restaurantService.getRestaurantByName("dominoz")).thenReturn(restaurants);
        String url = "/restaurant/name/dominoz";
        mvc.perform(get(url)
                .contentType(MediaType.asMediaType(APPLICATION_JSON)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$[0].restaurantName", Matchers.is("dominoz")));
    }

    @Test
    public void getRestaurantsByNameWithIncorrectCategoryName() throws Exception{
        Mockito.when(restaurantService.getRestaurantByCategory("indean")).thenReturn(null);
        String url = "/restaurant/category/indean";
        mvc.perform(get(url))
                .andExpect(status().is4xxClientError())
                .andExpect(content().string(containsString("No Restaurant under this category!")));
    }

    @Test
    public void getRestaurantsByCategory() throws Exception{
        RestaurantResponse restaurant = new RestaurantResponse();
        restaurant.setId(1);
        restaurant.setRestaurantName("dominoz");
        List<RestaurantResponse> restaurants = singletonList(restaurant);
        Mockito.when(restaurantService.getRestaurantByCategory("italian")).thenReturn(restaurants);
        String url = "/restaurant/category/italian";
        mvc.perform(get(url)
                .contentType(MediaType.asMediaType(APPLICATION_JSON)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$[0].restaurantName", Matchers.is("dominoz")));
    }

    @Test
    public void getRestaurantByIncorrectId() throws Exception{
        Mockito.when(restaurantService.getRestaurantDetails(1)).thenReturn(null);
        String url = "/restaurant/1";
        mvc.perform(get(url))
                .andExpect(status().is4xxClientError())
                .andExpect(content().string(containsString("No Restaurant by this id!")));
    }

    @Test
    public void getRestaurantById() throws Exception{
        RestaurantResponseCategorySet restaurant = new RestaurantResponseCategorySet();
        restaurant.setId(1);
        restaurant.setRestaurantName("dominoz");
        Mockito.when(restaurantService.getRestaurantDetails(1)).thenReturn(restaurant);
        String url = "/restaurant/1";
        mvc.perform(get(url))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.restaurantName", Matchers.is("dominoz")));
    }


    @Test
    public void updateRestaurantRatingWithoutLogin() throws Exception{
        String accessToken = "#############################";
        String rating = "4";
        Mockito.when(userAuthTokenService.isUserLoggedIn(accessToken)).thenReturn(null);
        String url = "/restaurant/1";
        mvc.perform(put(url)
                .param("rating",rating)
                .header("accessToken", accessToken))
                .andExpect(status().is4xxClientError())
                .andExpect(content().string(containsString("Please Login first to access this endpoint!")));
    }

    @Test
    public void updateRestaurantRatingWithLoggedOutUser() throws Exception{
        String accessToken = "#############################";
        UserAuthToken userAuthToken = new UserAuthToken();
        userAuthToken.setLogoutAt(now());
        String rating = "4";
        Mockito.when(userAuthTokenService.isUserLoggedIn(accessToken)).thenReturn(userAuthToken);
        String url = "/restaurant/1";
        mvc.perform(put(url)
                .param("rating",rating)
                .header("accessToken", accessToken))
                .andExpect(status().is4xxClientError())
                .andExpect(content().string(containsString("You have already logged out. Please Login first to access this endpoint!")));
    }

    @Test
    public void updateRestaurantRatingWithIncorrectId() throws Exception{
        String accessToken = "#############################";
        UserAuthToken userAuthToken = new UserAuthToken();
        String rating = "4";
        Mockito.when(userAuthTokenService.isUserLoggedIn(accessToken)).thenReturn(userAuthToken);
        Mockito.when(restaurantService.getRestaurantDetails(1)).thenReturn(null);
        String url = "/restaurant/1";
        mvc.perform(put(url)
                .contentType(MediaType.asMediaType(APPLICATION_JSON))
                .param("rating",rating)
                .header("accessToken", accessToken))
                .andExpect(status().is4xxClientError())
                .andExpect(content().string(containsString("No Restaurant by this id!")));
    }

    @Test
    public void updateRestaurantRating() throws Exception{
        String accessToken = "#############################";
        UserAuthToken userAuthToken = new UserAuthToken();
        RestaurantResponseCategorySet restaurant = new RestaurantResponseCategorySet();
        restaurant.setId(1);
        restaurant.setRestaurantName("dominoz");
        Restaurant restaurant1 = new Restaurant();
        restaurant1.setId(1);
        String rating = "4";
        restaurant1.setUserRating(4.2);
        restaurant1.setRestaurantName("dominoz");
        Mockito.when(userAuthTokenService.isUserLoggedIn(accessToken)).thenReturn(userAuthToken);
        Mockito.when(restaurantService.getRestaurantDetails(1)).thenReturn(restaurant);
        Mockito.when(restaurantService.updateRating(4, 1)).thenReturn(restaurant1);
        String url = "/restaurant/1";
        mvc.perform(put(url)
                .contentType(MediaType.asMediaType(APPLICATION_JSON))
                .param("rating",rating)
                .header("accessToken", accessToken))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.userRating", Matchers.is(4.2)))
                .andExpect(jsonPath("$.restaurantName", Matchers.is("dominoz")));
    }

}
