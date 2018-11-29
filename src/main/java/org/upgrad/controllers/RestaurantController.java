package org.upgrad.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.upgrad.services.RestaurantService;

import java.util.List;

@RestController
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping("/restaurant")
    public ResponseEntity<?> getAllRestaurants() {
        List restaurantList = restaurantService.getAllRestaurant();
        if (restaurantList.isEmpty()) {
            return ResponseEntity.ok(new String("There are no restaurants"));
        } else {
            return ResponseEntity.ok(restaurantList);
        }
    }
}
