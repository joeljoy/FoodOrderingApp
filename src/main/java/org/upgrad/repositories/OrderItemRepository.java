package org.upgrad.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.upgrad.models.OrderItem;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface OrderItemRepository extends CrudRepository<OrderItem, Integer> {

    @Query(nativeQuery = true, value = "SELECT * FROM order_item WHERE order_id = ?1")
    List<OrderItem> getByOrderId(Integer orderId);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO order_item(order_id, item_id, quantity, price) VALUES (?2, ?3, ?4, ?5)")
    void addOrderItem(Integer orderId, Integer itemId, Integer quantity, Integer price);
}
