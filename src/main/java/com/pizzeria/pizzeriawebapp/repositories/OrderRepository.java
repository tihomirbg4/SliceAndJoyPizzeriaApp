package com.pizzeria.pizzeriawebapp.repositories;

import com.pizzeria.pizzeriawebapp.models.Order;
import com.pizzeria.pizzeriawebapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);
}
