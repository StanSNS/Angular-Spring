package com.example.backend.Entity;

import com.example.backend.DTO.ProductDTO;
import com.example.backend.Entity.Base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Data
@Table(name = "products")
public class Product extends BaseEntity {
    private String name;
    private Long price;

    @Lob
    private String description;

    @Lob
    @Column(columnDefinition = "longblob")
    private byte[] image;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Category category;

    public ProductDTO getDTO() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(getId());
        productDTO.setName(getName());
        productDTO.setPrice(getPrice());
        productDTO.setDescription(getDescription());
        productDTO.setByteImage(getImage());
        productDTO.setCategoryId(getCategory().getId());
        productDTO.setCategoryName(getCategory().getName());
        return productDTO;

    }
}
