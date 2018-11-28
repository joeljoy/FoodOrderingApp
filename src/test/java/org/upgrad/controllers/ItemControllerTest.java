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
//import org.upgrad.models.Item;
//import org.upgrad.requestResponseEntity.RestaurantResponse;
//import org.upgrad.requestResponseEntity.RestaurantResponseCategorySet;
//import org.upgrad.services.ItemService;
//import org.upgrad.services.RestaurantService;
//
//import java.util.List;
//
//import static java.util.Collections.singletonList;
//import static org.hamcrest.Matchers.containsString;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//// This class contains all the test cases regarding the item controller
//@RunWith(SpringRunner.class)
//@WebMvcTest(ItemController.class)
//public class ItemControllerTest {
//
//    @Autowired
//    private MockMvc mvc;
//
//    @MockBean
//    private ItemService itemService;
//
//    @MockBean
//    private RestaurantService restaurantService;
//
//
//    @Test
//    public void getPopularItemsByIncorrectRestaurantId() throws Exception{
//        Mockito.when(restaurantService.getRestaurantDetails(1)).thenReturn(null);
//        String url = "/item/restaurant/1";
//        mvc.perform(get(url))
//                .andExpect(status().is4xxClientError())
//                .andExpect(content().string(containsString("No Restaurant by this id!")));
//    }
//
//    @Test
//    public void getPopularItemsByRestaurantId() throws Exception{
//        RestaurantResponseCategorySet restaurant = new RestaurantResponseCategorySet();
//        restaurant.setId(1);
//        restaurant.setRestaurantName("dominoz");
//        Item item = new Item();
//        item.setItemName("Pasta");
//        List<Item> items = singletonList(item);
//        Mockito.when(restaurantService.getRestaurantDetails(1)).thenReturn(restaurant);
//        Mockito.when(itemService.getItemByPopularity(1)).thenReturn(items);
//        String url = "/item/restaurant/1";
//        mvc.perform(get(url))
//                .andExpect(status().is2xxSuccessful())
//                .andExpect(jsonPath("$[0].itemName", Matchers.is("Pasta")));
//    }
//
//
//}
