package com.example.backend.DTO;

import lombok.Data;

@Data
public class AddProductInCartDTO {
    private Long userId;
    private Long productId;
}
