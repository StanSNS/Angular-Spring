package com.example.backend.Controller.admin;

import com.example.backend.DTO.ProductDTO;
import com.example.backend.Service.admin.product.AdminProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:4200")
public class AdminProductController {
    private final AdminProductService adminProductService;


    @PostMapping("/product")
    public ResponseEntity<ProductDTO> addProduct(@ModelAttribute ProductDTO productDTO) throws IOException {
        return ResponseEntity.status(CREATED).body(adminProductService.addProduct(productDTO));
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return ResponseEntity.ok(adminProductService.getAllProducts());
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<List<ProductDTO>> getAllProducts(@PathVariable String name) {
        return ResponseEntity.ok(adminProductService.getAllProductsByName(name));
    }

    @DeleteMapping("/product/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        boolean isDeleted = adminProductService.deleteProduct(productId);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
