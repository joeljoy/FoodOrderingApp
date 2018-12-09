package org.upgrad.services;

import org.upgrad.models.Order;
import org.upgrad.requestResponseEntity.OrderResponse;

import java.util.List;

public interface OrderService {
    List<OrderResponse> getPastOrders(Integer userId);
    Integer setOrder(Order order);
}
