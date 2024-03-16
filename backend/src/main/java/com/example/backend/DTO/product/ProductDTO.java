package com.example.backend.DTO.product;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProductDTO {
    private Long id;
    private String name;
    private Long price;
    private String description;
    private byte[] byteImage;
    private Long categoryId;
    private String categoryName;
    private MultipartFile image;
}
