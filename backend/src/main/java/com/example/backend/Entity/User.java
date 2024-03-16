package com.example.backend.Entity;

import com.example.backend.Entity.Base.BaseEntity;
import com.example.backend.Enums.UserRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User extends BaseEntity {

    private String email;
    private String password;
    private String name;
    private UserRole role;
    @Lob
    @Column(columnDefinition = "longblob")
    private byte[] image;
}
