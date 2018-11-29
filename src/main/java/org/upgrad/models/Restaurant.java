package org.upgrad.models;

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
    private String name;

    @Column(name = "photo_url", nullable = true)
    private String photoUrl;

    @Column(name = "user_rating")
    private Double userRating;

    @Column(name = "average_price_for_two")
    private Integer averagePrice;

    @Column(name = "number_of_users_rated")
    private Integer numberOfUsersRated;

//    @Column(name = "address_id")
//    private Integer addressId;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Address address;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Category> category = new ArrayList<>();

    public Restaurant() {
    }

    public Restaurant(String name, String photoUrl, Double userRating,
                      Integer averagePrice, Integer numberOfUsersRated) {
        this.name = name;
        this.photoUrl = photoUrl;
        this.userRating = userRating;
        this.averagePrice = averagePrice;
        this.numberOfUsersRated = numberOfUsersRated;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public List<Category> getCategory() {
        return category;
    }

    public void setCategory(List<Category> category) {
        this.category = category;
    }
}
