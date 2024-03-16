package com.example.backend.Service.cart;

import com.example.backend.DTO.AddProductInCartDTO;
import com.example.backend.DTO.CartItemsDTO;
import com.example.backend.DTO.OrderDTO;
import com.example.backend.Entity.CartItems;
import com.example.backend.Entity.Order;
import com.example.backend.Entity.Product;
import com.example.backend.Entity.User;
import com.example.backend.Enums.OrderStatus;
import com.example.backend.Repository.CartItemRepository;
import com.example.backend.Repository.OrderRepository;
import com.example.backend.Repository.ProductRepository;
import com.example.backend.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

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

        return orderDTO;
    }
}
