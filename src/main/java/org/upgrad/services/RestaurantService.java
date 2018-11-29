package org.upgrad.services;

import org.upgrad.models.Restaurant;
import org.upgrad.requestResponseEntity.RestaurantResponse;

import java.util.List;

public interface RestaurantService {
    List<RestaurantResponse> getAllRestaurant();
}
