package com.example.backend.Entity;

import com.example.backend.DTO.CartItemsDTO;
import com.example.backend.Entity.Base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Data
public class CartItems extends BaseEntity {

    private Long price;
    private Long quantity;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    @JsonIgnore
    private Order order;

    @JsonIgnore
    public CartItemsDTO getCartDTO() {
        CartItemsDTO cartItemsDTO = new CartItemsDTO();
        cartItemsDTO.setId(getId());
        cartItemsDTO.setPrice(getPrice());
        cartItemsDTO.setProductId(getProduct().getId());
        cartItemsDTO.setQuantity(getQuantity());
        cartItemsDTO.setUserId(getUser().getId());
        cartItemsDTO.setProductName(getProduct().getName());
        cartItemsDTO.setReturnedImg(getProduct().getImage());
        return cartItemsDTO;
    }

}
