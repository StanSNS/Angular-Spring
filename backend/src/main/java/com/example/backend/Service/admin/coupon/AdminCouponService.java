package com.example.backend.Service.admin.coupon;

import com.example.backend.Entity.Coupon;
import com.example.backend.Repository.CouponRepository;
import com.example.backend.exception.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminCouponService {

    private final CouponRepository couponRepository;

    public Coupon createCoupon(Coupon coupon) {
        if (couponRepository.existsByCode(coupon.getCode())) {
            throw new ValidationException("Coupon code already exists");
        }
        return couponRepository.save(coupon);
    }

    public List<Coupon> getAllCoupons(){
        return couponRepository.findAll();
    }

}
