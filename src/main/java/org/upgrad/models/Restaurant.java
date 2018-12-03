package org.upgrad.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "restaurant")
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "restaurant_name", nullable = false)
    private String restaurantName;

    @Column(name = "photo_url")
    private String photoUrl;

    @Column(name = "user_rating")
    private Double userRating;

    @Column(name = "average_price_for_two")
    private Integer averagePrice;

    @Column(name = "number_of_users_rated")
    private Integer numberOfUsersRated;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Address address;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "restaurant_category",
            joinColumns = {@JoinColumn(name = "restaurant_id")},
            inverseJoinColumns = {@JoinColumn(name = "category_id")}
    )
    @JsonIgnore
    private List<Category> categories = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "restaurant_item",
            joinColumns = {@JoinColumn(name = "restaurant_id")},
            inverseJoinColumns = {@JoinColumn(name = "item_id")}
    )
    @JsonIgnore
    private List<Item> items = new ArrayList<>();

    public Restaurant() {
    }

    public Restaurant(String restaurantName, String photoUrl, Double userRating,
                      Integer averagePrice, Integer numberOfUsersRated) {
        this.restaurantName = restaurantName;
        this.photoUrl = photoUrl;
        this.userRating = userRating;
        this.averagePrice = averagePrice;
        this.numberOfUsersRated = numberOfUsersRated;
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

    public Integer getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(Integer averagePrice) {
        this.averagePrice = averagePrice;
    }

    public Integer getNumberOfUsersRated() {
        return numberOfUsersRated;
    }

    public void setNumberOfUsersRated(Integer numberOfUsersRated) {
        this.numberOfUsersRated = numberOfUsersRated;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @JsonIgnore
    public List<Category> getCategory() {
        return categories;
    }

    @JsonIgnore
    public void setCategory(List<Category> category) {
        this.categories = category;
    }

    @JsonIgnore
    public List<Item> getItem() {
        return items;
    }

    @JsonIgnore
    public void setItem(List<Item> item) {
        this.items = item;
    }
}
