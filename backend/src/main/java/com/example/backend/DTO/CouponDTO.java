package com.example.backend.DTO;

import lombok.Data;

import java.util.Date;

@Data
public class CouponDTO {
    private String name;
    private String code;
    private Long discount;
    private Date expirationDate;
}
