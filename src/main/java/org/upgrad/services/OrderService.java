package org.upgrad.services;

import org.upgrad.requestResponseEntity.OrderResponse;

import java.util.List;

public interface OrderService {
    List<OrderResponse> getPastOrders(Integer user_id);
}
