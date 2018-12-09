package org.upgrad.services;

import org.springframework.stereotype.Service;
import org.upgrad.models.Order;
import org.upgrad.repositories.OrderRepository;
//import org.upgrad.requestResponseEntity.OrderResponse;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;

//    public OrderServiceImpl(OrderRepository orderRepository) {
//        this.orderRepository = orderRepository;
//    }
}
