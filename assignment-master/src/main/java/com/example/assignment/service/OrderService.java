package com.example.assignment.service;

import com.example.assignment.model.Order;
import com.example.assignment.model.OrderDto;
import com.example.assignment.model.User;
import com.example.assignment.model.UserListResponse;
import com.example.assignment.repository.OrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private ObjectMapper objectMapper;
    private HttpClient httpClient;

    public OrderService() {
        this.objectMapper = new ObjectMapper();
        this.httpClient = HttpClientBuilder.create().build();
    }

    @Autowired
    private OrderRepository orderRepository;

    public String createOrder(OrderDto orderDto) throws IOException {

        // Check if the email exists in http://reqres.in/api/users
        User user = checkEmailExists(orderDto.getEmail());
        if (user == null) {
            throw new IllegalArgumentException("Email does not exist");
        }
        boolean orderExists = orderRepository.existsByEmailAndProductId(orderDto.getEmail(), orderDto.getProductId());
        // Check if the customer has not ordered this product already
        if (orderExists) {
            throw new IllegalArgumentException("Customer has already ordered this product");
        }

        // Create a new order
        Order order = new Order();
        order.setEmail(orderDto.getEmail());
        order.setProductId(orderDto.getProductId());
        order.setFirstName(user.getFirst_name());
        order.setLastName(user.getLast_name());

        orderRepository.save(order);
        return order.getOrderId();
    }

    public User checkEmailExists(String email) throws IOException {
        String url = "http://reqres.in/api/users?page=1";
        int totalPages = 1;
        boolean emailFound = false;
        User user = null;

        while(!emailFound && totalPages > 0){
            HttpGet request = new HttpGet(url);
            HttpResponse response = httpClient.execute(request);

            UserListResponse userListResponse = objectMapper.readValue(response.getEntity().getContent(), UserListResponse.class);
            Optional<User> optionalUser = userListResponse.getData().stream()
                    .filter(u -> u.getEmail().equals(email))
                    .findFirst();
            if (optionalUser.isPresent()) {
                user = optionalUser.get();
                emailFound = true;
            } else {
                if (userListResponse.getPage() < userListResponse.getTotal_pages()) {
                    totalPages = userListResponse.getTotal_pages();
                    url = "http://reqres.in/api/users?page=" + (userListResponse.getPage() + 1);
                } else {
                    totalPages = 0;
                }
            }
        }

        return user;
    }

    public List<Order> getOrders() {
        return orderRepository.findAll();
    }
}