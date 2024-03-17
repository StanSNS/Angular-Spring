package com.example.backend.Service.cart;

import com.example.backend.DTO.AddProductInCartDTO;
import com.example.backend.DTO.OrderDTO;
import com.example.backend.DTO.PlaceOrderDTO;
import com.example.backend.Entity.*;
import com.example.backend.Enums.OrderStatus;
import com.example.backend.Repository.*;
import com.example.backend.exception.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final CouponRepository couponRepository;

    public ResponseEntity<?> addProductToCart(AddProductInCartDTO addProductInCartDTO) {
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(addProductInCartDTO.getUserId(), OrderStatus.Pending);
        Optional<CartItems> byProductIdAndOrderIdAndUserId = cartItemRepository
                .findByProductIdAndOrderIdAndUserId(
                        addProductInCartDTO.getProductId(),
                        activeOrder.getId(),
                        addProductInCartDTO.getUserId()
                );

        if (byProductIdAndOrderIdAndUserId.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        } else {
            Optional<Product> productOptional = productRepository.findById(addProductInCartDTO.getProductId());
            Optional<User> userOptional = userRepository.findById(addProductInCartDTO.getUserId());

            if (productOptional.isPresent() && userOptional.isPresent()) {
                CartItems cartItems = new CartItems();
                cartItems.setProduct(productOptional.get());
                cartItems.setPrice(productOptional.get().getPrice());
                cartItems.setQuantity(1L);
                cartItems.setUser(userOptional.get());
                cartItems.setOrder(activeOrder);

                CartItems updatedCart = cartItemRepository.save(cartItems);
                activeOrder.setTotalAmount(activeOrder.getTotalAmount() + cartItems.getPrice());
                activeOrder.setAmount(activeOrder.getAmount() + cartItems.getPrice());
                activeOrder.getCartItems().add(cartItems);

                orderRepository.save(activeOrder);
                return ResponseEntity.status(HttpStatus.CREATED).body(updatedCart);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or product not found.");
            }
        }
    }

    public OrderDTO getCartByUserId(Long id) {
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(id, OrderStatus.Pending);

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setAmount(activeOrder.getAmount());
        orderDTO.setId(activeOrder.getId());
        orderDTO.setOrderStatus(activeOrder.getOrderStatus());
        orderDTO.setDiscount(activeOrder.getDiscount());
        orderDTO.setTotalAmount(activeOrder.getTotalAmount());
        orderDTO.setCartItems(activeOrder.getCartItems().stream().map(CartItems::getCartDTO).collect(Collectors.toList()));

        if (activeOrder.getCoupon() != null) {
            orderDTO.setCouponName(activeOrder.getCoupon().getName());
        }

        return orderDTO;
    }

    public OrderDTO applyCoupon(Long userId, String code) {
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(userId, OrderStatus.Pending);
        Coupon coupon = couponRepository.findByCode(code).orElseThrow(() -> new ValidationException("Coupon not found."));

        if (couponIsExpired(coupon)) {
            throw new ValidationException("Coupon has expired");
        }

        double discountAmount = ((coupon.getDiscount() / 100.0) * activeOrder.getTotalAmount());
        double netAmount = activeOrder.getTotalAmount() - discountAmount;
        activeOrder.setAmount((long) netAmount);
        activeOrder.setDiscount((long) discountAmount);
        activeOrder.setCoupon(coupon);

        orderRepository.save(activeOrder);

        return activeOrder.getOrderDTO();
    }

    private boolean couponIsExpired(Coupon coupon) {
        Date date = new Date();
        Date expirationDate = coupon.getExpirationDate();
        return expirationDate != null && date.after(expirationDate);
    }

    public OrderDTO increaseProductQuantity(AddProductInCartDTO addProductInCartDTO) {
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(addProductInCartDTO.getUserId(), OrderStatus.Pending);
        Optional<Product> productOptional = productRepository.findById(addProductInCartDTO.getProductId());

        Optional<CartItems> optionalCartItems = cartItemRepository.findByProductIdAndOrderIdAndUserId(
                addProductInCartDTO.getProductId(), activeOrder.getId(), addProductInCartDTO.getUserId());

        if (productOptional.isPresent() && optionalCartItems.isPresent()) {
            CartItems cartItems = optionalCartItems.get();
            Product product = productOptional.get();

            activeOrder.setAmount(activeOrder.getAmount() + product.getPrice());
            activeOrder.setTotalAmount(activeOrder.getTotalAmount() + product.getPrice());
            cartItems.setQuantity((cartItems.getQuantity() + 1));

            if (activeOrder.getCoupon() != null) {
                double discountAmount = ((activeOrder.getCoupon().getDiscount() / 100.0) * activeOrder.getTotalAmount());
                double netAmount = activeOrder.getTotalAmount() - discountAmount;
                activeOrder.setAmount((long) netAmount);
                activeOrder.setDiscount((long) discountAmount);
            }

            cartItemRepository.save(cartItems);
            orderRepository.save(activeOrder);
            return activeOrder.getOrderDTO();
        }
        return null;
    }

    public OrderDTO decreaseProductQuantity(AddProductInCartDTO addProductInCartDTO) {
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(addProductInCartDTO.getUserId(), OrderStatus.Pending);
        Optional<Product> productOptional = productRepository.findById(addProductInCartDTO.getProductId());

        Optional<CartItems> optionalCartItems = cartItemRepository.findByProductIdAndOrderIdAndUserId(
                addProductInCartDTO.getProductId(), activeOrder.getId(), addProductInCartDTO.getUserId());

        if (productOptional.isPresent() && optionalCartItems.isPresent()) {
            CartItems cartItems = optionalCartItems.get();
            Product product = productOptional.get();

            activeOrder.setAmount(activeOrder.getAmount() - product.getPrice());
            activeOrder.setTotalAmount(activeOrder.getTotalAmount() - product.getPrice());
            cartItems.setQuantity((cartItems.getQuantity() - 1));

            if (activeOrder.getCoupon() != null) {
                double discountAmount = ((activeOrder.getCoupon().getDiscount() / 100.0) * activeOrder.getTotalAmount());
                double netAmount = activeOrder.getTotalAmount() - discountAmount;
                activeOrder.setAmount((long) netAmount);
                activeOrder.setDiscount((long) discountAmount);
            }

            cartItemRepository.save(cartItems);
            orderRepository.save(activeOrder);
            return activeOrder.getOrderDTO();
        }
        return null;
    }

    public OrderDTO placeOrder(PlaceOrderDTO placeOrderDTO) {
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(placeOrderDTO.getUserId(), OrderStatus.Pending);
        Optional<User> optionalUser = userRepository.findById(placeOrderDTO.getUserId());

        if (activeOrder == null && optionalUser.isPresent()) {
            Order order = new Order();
            order.setAmount(0L);
            order.setTotalAmount(0L);
            order.setDiscount(0L);
            order.setUser(optionalUser.get());
            order.setOrderStatus(OrderStatus.Pending);
            orderRepository.save(order);

            return order.getOrderDTO();
        } else if (activeOrder != null && optionalUser.isPresent()) {
            activeOrder.setOrderDescription(placeOrderDTO.getOrderDescription());
            activeOrder.setAddress(placeOrderDTO.getAddress());
            activeOrder.setDate(new Date());
            activeOrder.setOrderStatus(OrderStatus.Placed);
            activeOrder.setTrackingId(UUID.randomUUID());

            orderRepository.save(activeOrder);
            return activeOrder.getOrderDTO();
        }
        return null;
    }

    public List<OrderDTO> getMyPlacedOrders(Long userId) {
        return orderRepository
                .findByUserIdAndOrderStatusIn(userId, List.of(OrderStatus.Placed, OrderStatus.Shipped, OrderStatus.Delivered))
                .stream()
                .map(Order::getOrderDTO)
                .collect(Collectors.toList());
    }

}
