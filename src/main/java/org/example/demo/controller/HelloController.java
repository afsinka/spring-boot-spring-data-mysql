package org.example.demo.controller;

import org.example.demo.entity.Order;
import org.example.demo.entity.User;
import org.example.demo.model.CreateUserRequest;
import org.example.demo.repository.CustomerRepository;
import org.example.demo.repository.OrderRepository;
import org.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
public class HelloController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/hello")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @GetMapping("/users")
    public @ResponseBody Iterable<User> findAll() {
        return userRepository.findAll();
    }

    @PostMapping(path = "/users")
    public @ResponseBody User addNewUser(@RequestBody CreateUserRequest userRequest) {
        User user = new User();
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        return userRepository.save(user);
    }

    @GetMapping("/users/{id}")
    public @ResponseBody User findUser(@PathVariable Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    //e.g. http://localhost:8081/orders?customerUuid=6372b329-4694-4b3a-a56a-7a4f51144b0c
    @GetMapping("/orders")
    public @ResponseBody List<Order> getAllOrdersOfCustomer(@RequestParam(required = false) UUID customerUuid) {
        var customer = customerRepository.findByUuid(customerUuid).orElseThrow();
        return orderRepository.getOrdersByCustomerId(customer.getId());
    }

}