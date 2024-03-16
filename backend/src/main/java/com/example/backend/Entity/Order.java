package com.example.backend.Entity;

import com.example.backend.Entity.Base.BaseEntity;
import com.example.backend.Enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Data
public class Order extends BaseEntity {
    private String orderDescription;
    private Date date;
    private Long amount;
    private String address;
    private String payment;
    private OrderStatus orderStatus;
    private Long totalAmount;
    private Long discount;
    private UUID trackingId;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonIgnore
    private User user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
    @JsonIgnore
    private List<CartItems> cartItems;

    @Override
    public String toString() {
        return "Order{" +
                "id=" + getId() +
                ", orderDescription='" + orderDescription + '\'' +
                ", date=" + date +
                ", amount=" + amount +
                ", address='" + address + '\'' +
                ", payment='" + payment + '\'' +
                ", orderStatus=" + orderStatus +
                ", totalAmount=" + totalAmount +
                ", discount=" + discount +
                ", trackingId=" + trackingId +
                '}';
    }
}
