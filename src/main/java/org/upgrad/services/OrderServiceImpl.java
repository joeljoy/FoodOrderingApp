package org.upgrad.services;

import org.springframework.stereotype.Service;
import org.upgrad.models.Item;
import org.upgrad.models.Order;
import org.upgrad.models.OrderItem;
import org.upgrad.repositories.OrderItemRepository;
import org.upgrad.repositories.OrderRepository;
import org.upgrad.requestResponseEntity.ItemResponse;
import org.upgrad.requestResponseEntity.OrderItemResponse;
import org.upgrad.requestResponseEntity.OrderResponse;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;
    private OrderItemRepository orderItemRepository;

    public OrderServiceImpl(OrderRepository orderRepository, OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public List<OrderResponse> getPastOrders(Integer userId) {
        return getOrderResponse(orderRepository.getPastOrders(userId));
    }

    @Override
    public Integer setOrder(Order order) {
        orderRepository.save(order);
        return order.getId();
    }

    private List<OrderResponse> getOrderResponse(List<Order> orders) {
        List<OrderResponse> orderResponseList = new ArrayList<>();
        for (Order order: orders) {
            OrderResponse orderResponse = new OrderResponse(
                    order.getId(),
                    order.getBill(),
                    order.getCoupon(),
                    order.getDiscount(),
                    order.getDate(),
                    order.getPayment(),
                    order.getUser(),
                    order.getAddress(),
                    getOrderItemResponse(orderItemRepository.getByOrderId(order.getId()))
            );
            orderResponseList.add(orderResponse);
        }
        return orderResponseList;
    }

    private List<OrderItemResponse> getOrderItemResponse(List<OrderItem> orderItems) {
        List<OrderItemResponse> orderItemResponseList = new ArrayList<>();
        for (OrderItem orderItem: orderItems) {
            OrderItemResponse orderItemResponse = new OrderItemResponse(
                    orderItem.getId(),
                    getItemReponse(orderItem.getItem()),
                    orderItem.getQuantity(),
                    orderItem.getPrice()
            );
            orderItemResponseList.add(orderItemResponse);
        }
        return orderItemResponseList;
    }

    private ItemResponse getItemReponse(Item item) {
        ItemResponse itemResponse = new ItemResponse(
                item.getId(),
                item.getItemName(),
                item.getPrice(),
                item.getType()
        );
        return itemResponse;
    }
}
