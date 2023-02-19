package com.example.assignment.controller;

import com.example.assignment.model.Order;
import com.example.assignment.model.OrderDto;
import com.example.assignment.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("")
    public ResponseEntity<String> createOrder(@RequestBody OrderDto orderDto) {
        try {
            String orderId = orderService.createOrder(orderDto);
            return ResponseEntity.ok(orderId);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("")
    public List<Order> getOrders() {
        return orderService.getOrders();
    }
}