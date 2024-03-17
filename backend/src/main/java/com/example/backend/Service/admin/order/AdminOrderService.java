package com.example.backend.Service.admin.order;

import com.example.backend.DTO.OrderDTO;
import com.example.backend.Entity.Order;
import com.example.backend.Enums.OrderStatus;
import com.example.backend.Repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminOrderService {
    private final OrderRepository orderRepository;

    public List<OrderDTO> getAllOrders() {
        return orderRepository
                .findAllByOrderStatusIn(List.of(OrderStatus.Placed, OrderStatus.Shipped, OrderStatus.Delivered))
                .stream()
                .map(Order::getOrderDTO)
                .collect(Collectors.toList());
    }

    public OrderDTO changeOrderStatus(Long orderId, String status) {
        Optional<Order> byId = orderRepository.findById(orderId);

        if (byId.isPresent()) {
            Order order = byId.get();

            if (Objects.equals(status, "Shipped")) {
                order.setOrderStatus(OrderStatus.Shipped);
            } else if (Objects.equals(status, "Delivered")) {
                order.setOrderStatus(OrderStatus.Delivered);
            }
            return orderRepository.save(order).getOrderDTO();
        }
        return null;
    }

}
