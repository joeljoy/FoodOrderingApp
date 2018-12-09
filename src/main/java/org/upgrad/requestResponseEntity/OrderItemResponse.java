package org.upgrad.requestResponseEntity;

import org.upgrad.models.Item;

public class OrderItemResponse {

    private Integer id;
    private ItemResponse item;
    private Integer quantity;
    private Integer price;

    public OrderItemResponse() {
    }

    public OrderItemResponse(Integer id, ItemResponse item, Integer quantity, Integer price) {
        this.id = id;
        this.item = item;
        this.quantity = quantity;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ItemResponse getItem() {
        return item;
    }

    public void setItem(ItemResponse item) {
        this.item = item;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
