package org.upgrad.requestResponseEntity;

/*
 * ItemQuantity  class contain contains the attributes itemId and its corresponding quantity. It will be used to take request during saving any order details.
 * Here getter, setter and constructor are defined for this request class.
 */
public class ItemQuantity {

    private Integer itemId;
    private Integer quantity;

    public ItemQuantity(){}

    public ItemQuantity(Integer itemId, Integer quantity) {
        this.itemId = itemId;
        this.quantity = quantity;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
