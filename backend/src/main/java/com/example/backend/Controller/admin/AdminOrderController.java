package com.example.backend.Controller.admin;

import com.example.backend.DTO.OrderDTO;
import com.example.backend.Service.admin.order.AdminOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:4200")
public class AdminOrderController {

    private final AdminOrderService adminOrderService;

    @GetMapping("/placedOrders")
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        return ResponseEntity.ok(adminOrderService.getAllOrders());
    }

    @GetMapping("/order/{orderId}/{status}")
    public ResponseEntity<?> changeOrderStatus(@PathVariable Long orderId, @PathVariable String status) {
        OrderDTO orderDTO = adminOrderService.changeOrderStatus(orderId, status);
        if (orderDTO == null) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.status(HttpStatus.OK).body(orderDTO);
    }

}
