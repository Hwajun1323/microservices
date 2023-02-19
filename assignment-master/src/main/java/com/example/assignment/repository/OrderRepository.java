package com.example.assignment.repository;

import com.example.assignment.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {
    boolean existsByEmailAndProductId(String email, String productId);
}