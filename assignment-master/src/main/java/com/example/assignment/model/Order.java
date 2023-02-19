package com.example.assignment.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "orders")
public class Order {
    @Id
    private String orderId;
    private String email;
    private String firstName;
    private String lastName;
    private String productId;
}
