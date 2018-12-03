package org.upgrad.services;

import org.springframework.stereotype.Service;
import org.upgrad.models.Category;
import org.upgrad.models.Restaurant;
import org.upgrad.repositories.RestaurantRepository;
import org.upgrad.requestResponseEntity.CategoryResponse;
import org.upgrad.requestResponseEntity.RestaurantResponse;
import org.upgrad.requestResponseEntity.RestaurantResponseCategorySet;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class RestaurantServiceImpl implements RestaurantService {

    private RestaurantRepository restaurantRepository;

    public RestaurantServiceImpl(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public List<RestaurantResponse> getAllRestaurant() {
        return getRestaurantResponseFromRestaurant(restaurantRepository.findAll());
    }

    @Override
    public List<RestaurantResponse> getRestaurantByName(String restaurantName) {
        return getRestaurantResponseFromRestaurant(restaurantRepository.findByName(restaurantName));
    }

    @Override
    public List<RestaurantResponse> getRestaurantByCategory(String categoryName) {
        return getRestaurantResponseFromRestaurant(restaurantRepository.findByCategory(categoryName));
    }

    @Override
    public RestaurantResponseCategorySet getRestaurantDetails(Integer restaurantId) {
        RestaurantResponseCategorySet response = new RestaurantResponseCategorySet();
        Restaurant restaurant = restaurantRepository.find(restaurantId);

        Set<CategoryResponse> categoryResponseSet = new HashSet<>();
        for (Category category : restaurant.getCategory()) {
            CategoryResponse categoryResponse = new CategoryResponse();
            categoryResponse.setCategoryName(category.getCategoryName());
            categoryResponse.setId(category.getId());
            categoryResponse.setItems(convertListToSet(category.getItem()));
            categoryResponseSet.add(categoryResponse);
        }

        response.setId(restaurant.getId());
        response.setRestaurantName(restaurant.getRestaurantName());
        response.setPhotoUrl(restaurant.getPhotoUrl());
        response.setAddress(restaurant.getAddress());
        response.setAvgPrice(restaurant.getAveragePrice());
        response.setNumberUsersRated(restaurant.getNumberOfUsersRated());
        response.setUserRating(restaurant.getUserRating());
        response.setCategories(categoryResponseSet);
        return response;
    }

    @Override
    public Restaurant updateRating(Integer userRating, Integer restaurantId) {
        restaurantRepository.updateRestaurantById(userRating, restaurantId);
        return restaurantRepository.find(restaurantId);
    }

    private <T> Set<T> convertListToSet(List<T> list) {
        return list.stream().collect(Collectors.toSet());
    }

    private List<RestaurantResponse> getRestaurantResponseFromRestaurant(List<Restaurant> restaurants) {
        List<RestaurantResponse> restaurantResponseList = new ArrayList<>();
        for (Restaurant restaurant : restaurants) {
            RestaurantResponse response = new RestaurantResponse(
                    restaurant.getId(),
                    restaurant.getRestaurantName(),
                    restaurant.getPhotoUrl(),
                    restaurant.getUserRating(),
                    restaurant.getAveragePrice(),
                    restaurant.getNumberOfUsersRated(),
                    restaurant.getAddress(),
                    ""
            );
            StringBuilder builder = new StringBuilder();
            for (Category category : restaurant.getCategory()) {
                builder.append(category.getCategoryName());
                builder.append(",");
            }
            response.setCategories(builder.toString());
            restaurantResponseList.add(response);
        }
        return restaurantResponseList;
    }
}
