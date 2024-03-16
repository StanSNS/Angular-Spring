package com.example.backend.Service.customer;

import com.example.backend.DTO.product.ProductDTO;
import com.example.backend.Entity.Product;
import com.example.backend.Repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerProductService {

    private final ProductRepository productRepository;

    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream().map(Product::getDTO).collect(Collectors.toList());
    }

    public List<ProductDTO> getAllProductsByName(String name) {
        return productRepository.findAllByNameContaining(name).stream().map(Product::getDTO).collect(Collectors.toList());
    }
}
