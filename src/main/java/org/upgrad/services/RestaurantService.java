package org.upgrad.services;

import org.upgrad.models.Restaurant;
import org.upgrad.requestResponseEntity.RestaurantResponse;
import org.upgrad.requestResponseEntity.RestaurantResponseCategorySet;

import java.util.List;

public interface RestaurantService {
    List<RestaurantResponse> getAllRestaurant();

    List<RestaurantResponse> getRestaurantByName(String restaurantName);

    List<RestaurantResponse> getRestaurantByCategory(String categoryName);

    RestaurantResponseCategorySet getRestaurantDetails(Integer restaurantId);

    Restaurant updateRating(Integer rating, Integer restaurantId);
}
