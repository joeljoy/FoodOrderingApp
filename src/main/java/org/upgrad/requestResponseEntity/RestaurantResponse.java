package org.upgrad.requestResponseEntity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.upgrad.models.Address;

import javax.persistence.*;
import java.util.Set;

/*
 * RestaurantResponse class contain all the attributes that are to be returned as a response.
 * Here getter, setter and constructor are defined for this response class.
 */
public class RestaurantResponse {

    private Integer id;

    private String restaurantName;

    private String photoUrl;

    private Double userRating;

    private Integer avgPrice;

    private Integer numberUsersRated;

    private Address address;

    private String categories;

    public RestaurantResponse(Integer id, String restaurantName, String photoUrl, Double userRating, Integer avgPrice, Integer numberUsersRated, Address address, String categories) {
        this.id = id;
        this.restaurantName = restaurantName;
        this.photoUrl = photoUrl;
        this.userRating = userRating;
        this.avgPrice = avgPrice;
        this.numberUsersRated = numberUsersRated;
        this.address = address;
        this.categories = categories;
    }

    public RestaurantResponse() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public Double getUserRating() {
        return userRating;
    }

    public void setUserRating(Double userRating) {
        this.userRating = userRating;
    }

    public Integer getAvgPrice() {
        return avgPrice;
    }

    public void setAvgPrice(Integer avgPrice) {
        this.avgPrice = avgPrice;
    }

    public Integer getNumberUsersRated() {
        return numberUsersRated;
    }

    public void setNumberUsersRated(Integer numberUsersRated) {
        this.numberUsersRated = numberUsersRated;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }
}
