package org.upgrad.requestResponseEntity;

import org.upgrad.models.Item;

import java.util.Set;

/*
 * CategoryResponse  class contain all the attributes that are to be returned as a response.
 * Here getter, setter and constructor are defined for this response class.
 */
public class CategoryResponse {

    private Integer id;

    private String categoryName;

    private Set<Item> items;

    public CategoryResponse() {
    }

    public CategoryResponse(Integer id, String categoryName, Set<Item> items) {
        this.id = id;
        this.categoryName = categoryName;
        this.items = items;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }
}
