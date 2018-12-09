package org.upgrad.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.upgrad.models.Order;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<Order, Integer> {

    @Query(nativeQuery = true, value = "SELECT * FROM orders WHERE (user_id) = (?1)")
    List<Order> getPastOrders(Integer userId);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE orders SET id = ?1, bill = ?2, coupon_id = ?3, discount = ?4, date = ?5, payment_id = ?6, user_id = ?7, address_id = ?8")
    void setOrder(Integer id, Double bill, Integer couponId, Double discount, Date date, Integer paymentId, Integer UserId, Integer addressId);
}
