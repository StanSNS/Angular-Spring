package com.example.backend.Entity;

import com.example.backend.Entity.Base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "category")
public class Category extends BaseEntity {

    private String name;

    @Column(columnDefinition = "LONGTEXT")
    private String description;

}
