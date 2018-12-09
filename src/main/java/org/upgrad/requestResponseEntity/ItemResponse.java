package org.upgrad.requestResponseEntity;

public class ItemResponse {

    private Integer id;
    private String itemName;
    private Integer price;
    private String type;

    public ItemResponse() {
    }

    public ItemResponse(Integer id, String itemName, Integer price, String type) {
        this.id = id;
        this.itemName = itemName;
        this.price = price;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
}
