package org.upgrad.services;

import org.springframework.stereotype.Service;
import org.upgrad.models.Category;
import org.upgrad.models.Restaurant;
import org.upgrad.repositories.RestaurantRepository;
import org.upgrad.requestResponseEntity.RestaurantResponse;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class RestaurantServiceImpl implements RestaurantService{

    private RestaurantRepository restaurantRepository;

    public RestaurantServiceImpl(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public List<RestaurantResponse> getAllRestaurant() {
        List<RestaurantResponse> restaurantResponseList = new ArrayList<>();
        for (Restaurant restaurant : restaurantRepository.findAll()){
            RestaurantResponse response = new RestaurantResponse(
                    restaurant.getId(),
                    restaurant.getName(),
                    restaurant.getPhotoUrl(),
                    restaurant.getUserRating(),
                    restaurant.getAveragePrice(),
                    restaurant.getNumberOfUsersRated(),
                    restaurant.getAddress(),
                    ""
            );
            StringBuilder builder = new StringBuilder();
            for (Category category : restaurant.getCategory()){
                builder.append(category.getCategoryName());
                builder.append(",");
            }
            response.setCategories(builder.toString());
            restaurantResponseList.add(response);
        }
        return restaurantResponseList;
    }
}
