package org.upgrad.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.upgrad.models.Restaurant;
import org.upgrad.requestResponseEntity.RestaurantResponseCategorySet;
import org.upgrad.services.RestaurantService;
import org.upgrad.services.UserAuthTokenService;

import java.util.List;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private UserAuthTokenService userAuthTokenService;

    @GetMapping()
    public ResponseEntity<?> getAllRestaurants() {
        List restaurantList = restaurantService.getAllRestaurant();
        if (restaurantList.isEmpty()) {
            return ResponseEntity.ok("There are no restaurants!");
        } else {
            return ResponseEntity.ok(restaurantList);
        }
    }

    @GetMapping("/name/{restaurantName}")
    public ResponseEntity<?> getRestaurantByName(@PathVariable("restaurantName") String restaurantName) {
        List restaurantList = restaurantService.getRestaurantByName(restaurantName);
        if (restaurantList == null || restaurantList.isEmpty()) {
            return new ResponseEntity<>("No Restaurant by this name!", HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.ok(restaurantList);
        }
    }

    @GetMapping("category/{categoryName}")
    public ResponseEntity<?> getRestaurantByCategory(@PathVariable("categoryName") String categoryName) {
        List restaurantList = restaurantService.getRestaurantByCategory(categoryName);
        if (restaurantList == null || restaurantList.isEmpty()) {
            return new ResponseEntity<>("No Restaurant under this category!", HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.ok(restaurantList);
        }
    }

    @GetMapping("{restaurantId}")
    public ResponseEntity<?> getRestaurantDetails(@PathVariable("restaurantId") Integer restaurantId) {
        RestaurantResponseCategorySet responseCategory = restaurantService.getRestaurantDetails(restaurantId);
        if (responseCategory == null) {
            return new ResponseEntity<>("No Restaurant by this id!", HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.ok(responseCategory);
        }
    }

    @PutMapping("{restaurantId}")
    public ResponseEntity<?> updateRestaurantDetails(@PathVariable("restaurantId") Integer restaurantId, @RequestHeader String accessToken, @RequestParam Integer rating) {
        if (userAuthTokenService.isUserLoggedIn(accessToken) == null) {
            return new ResponseEntity<>("Please Login first to access this endpoint!", HttpStatus.UNAUTHORIZED);
        } else if (userAuthTokenService.isUserLoggedIn(accessToken).getLogoutAt() != null) {
            return new ResponseEntity<>("You have already logged out. Please Login first to access this endpoint!", HttpStatus.UNAUTHORIZED);
        } else {
            RestaurantResponseCategorySet response = restaurantService.getRestaurantDetails(restaurantId);
            if (response == null) {
                return new ResponseEntity<>("No Restaurant by this id!", HttpStatus.NOT_FOUND);
            }
            Restaurant result = restaurantService.updateRating(rating, restaurantId);
            return ResponseEntity.ok(result);
        }

    }

}
