package com.example.backend.DTO.auth;

import com.example.backend.Enums.UserRole;
import lombok.Data;

@Data
public class UserDTO {

    private Long id;
    private String email;
    private String name;
    private UserRole userRole;
}
