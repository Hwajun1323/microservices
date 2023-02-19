package com.example.assignment.model;

import lombok.Data;

@Data
public class User {

    private Long id;
    private String email;
    private String first_name;
    private String last_name;
    private String avatar;
}
