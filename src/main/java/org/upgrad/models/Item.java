package org.upgrad.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "price")
    private Integer price;

    @Column(name = "type")
    private String type;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<OrderItem> orderItems;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "restaurant_item",
            joinColumns = {@JoinColumn(name = "restaurant_id")},
            inverseJoinColumns = {@JoinColumn(name = "item_id")}
    )
    @JsonIgnore
    private List<Restaurant> restaurant = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "category_item",
            joinColumns = {@JoinColumn(name = "item_id")},
            inverseJoinColumns = {@JoinColumn(name = "category_id")}
    )
    @JsonIgnore
    private List<Category> categories = new ArrayList<>();


    public Item() {
    }

    public Item(String itemName, Integer price, String type) {
        this.itemName = itemName;
        this.price = price;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @JsonIgnore
    public List<Restaurant> getRestaurant() {
        return restaurant;
    }

    @JsonIgnore
    public void setRestaurant(List<Restaurant> restaurant) {
        this.restaurant = restaurant;
    }

    @JsonIgnore
    public List<Category> getCategory() {
        return categories;
    }

    @JsonIgnore
    public void setCategory(List<Category> category) {
        this.categories = category;
    }
}
