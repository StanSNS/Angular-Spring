package com.example.backend.Service.admin.product;

import com.example.backend.DTO.ProductDTO;
import com.example.backend.Entity.Category;
import com.example.backend.Entity.Product;
import com.example.backend.Repository.CategoryRepository;
import com.example.backend.Repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductDTO addProduct(ProductDTO productDTO) throws IOException {
        Product product = new Product();

        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setImage(productDTO.getImage().getBytes());

        Category category = categoryRepository.findById(productDTO.getCategoryId()).orElseThrow();
        product.setCategory(category);
        return productRepository.save(product).getDTO();
    }

    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream().map(Product::getDTO).collect(Collectors.toList());
    }

    public List<ProductDTO> getAllProductsByName(String name) {
        return productRepository.findAllByNameContaining(name).stream().map(Product::getDTO).collect(Collectors.toList());
    }

    public boolean deleteProduct(Long id) {
        Optional<Product> byId = productRepository.findById(id);
        if (byId.isPresent()) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }


}
