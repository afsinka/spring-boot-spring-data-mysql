package org.example.demo.repository;

import org.example.demo.entity.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
    List<Order> getOrdersByCustomerId(Long customerId);
}
