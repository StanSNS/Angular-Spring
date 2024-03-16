package com.example.backend.DTO.auth;

import lombok.Data;

@Data
public class RegisterDTO {

    private String email;
    private String password;
    private String name;
}
