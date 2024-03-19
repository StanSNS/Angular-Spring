package com.example.backend.Controller.customer;

import com.example.backend.DTO.AddProductInCartDTO;
import com.example.backend.DTO.OrderDTO;
import com.example.backend.DTO.PlaceOrderDTO;
import com.example.backend.Service.cart.CartService;
import com.example.backend.exception.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/customer")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:4200")
public class CartController {

    private final CartService cartService;

    @PostMapping("/cart")
    public ResponseEntity<?> addProductToCart(@RequestBody AddProductInCartDTO addProductInCartDTO) {
        return cartService.addProductToCart(addProductInCartDTO);
    }

    @GetMapping("/cart/{userId}")
    public ResponseEntity<OrderDTO> getCartByUserName(@PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(cartService.getCartByUserId(userId));
    }

    @GetMapping("/coupon/{userId}/{code}")
    public ResponseEntity<?> applyCoupon(@PathVariable Long userId, @PathVariable String code) {
        try {
            return ResponseEntity.ok(cartService.applyCoupon(userId, code));
        } catch (ValidationException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @PostMapping("/addition")
    public ResponseEntity<OrderDTO> increaseProductQuantity(@RequestBody AddProductInCartDTO addProductInCartDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cartService.increaseProductQuantity(addProductInCartDTO));
    }

    @PostMapping("/deduction")
    public ResponseEntity<OrderDTO> decreaseProductQuantity(@RequestBody AddProductInCartDTO addProductInCartDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cartService.decreaseProductQuantity(addProductInCartDTO));
    }

    @PostMapping("/placeOrder")
    public ResponseEntity<OrderDTO> placeOrder(@RequestBody PlaceOrderDTO placeOrderDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cartService.placeOrder(placeOrderDTO));
    }

}
