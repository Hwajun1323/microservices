package com.example.assignment.model;

import lombok.Data;

import java.util.List;

@Data
public class UserListResponse {
    private int page;
    private int per_page;
    private int total;
    private int total_pages;
    private List<User> data;
    private Support support;

    @Data
    public static class Support {
        private String url;
        private String text;
    }
}

