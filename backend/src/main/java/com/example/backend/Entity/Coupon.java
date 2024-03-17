package com.example.backend.Entity;


import com.example.backend.Entity.Base.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "coupons")
@Data
public class Coupon extends BaseEntity {
    private String name;
    private String code;
    private Long discount;
    private Date expirationDate;
}
