package com.example.backend.Controller.customer;

import com.example.backend.DTO.product.ProductDTO;
import com.example.backend.Service.customer.CustomerProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/customer")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:4200")
public class CustomerProductController {

    private final CustomerProductService customerProductService;

    @GetMapping("/products")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return ResponseEntity.ok(customerProductService.getAllProducts());
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<List<ProductDTO>> getAllProducts(@PathVariable String name) {
        return ResponseEntity.ok(customerProductService.getAllProductsByName(name));
    }
}
