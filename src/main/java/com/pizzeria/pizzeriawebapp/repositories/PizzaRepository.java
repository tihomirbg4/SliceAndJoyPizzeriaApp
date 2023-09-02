package com.pizzeria.pizzeriawebapp.repositories;

import com.pizzeria.pizzeriawebapp.models.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface PizzaRepository extends JpaRepository<Pizza, Long> {

    Pizza findPizzaById(Long id);
    void deletePizzaById(Long id);
//    List<Pizza> findAllById(Set<Long> productIds);
}
