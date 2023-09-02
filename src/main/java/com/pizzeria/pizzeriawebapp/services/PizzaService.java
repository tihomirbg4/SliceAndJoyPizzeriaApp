package com.pizzeria.pizzeriawebapp.services;

import com.pizzeria.pizzeriawebapp.dtos.PizzaDTO;
import com.pizzeria.pizzeriawebapp.models.Pizza;

import java.util.List;
import java.util.Set;

public interface PizzaService {

    List<com.pizzeria.pizzeriawebapp.dtos.PizzaDTO> getAllPizzas();

    com.pizzeria.pizzeriawebapp.dtos.PizzaDTO findPizzaById(Long id);

    com.pizzeria.pizzeriawebapp.dtos.PizzaDTO createPizza(Pizza pizza);

    com.pizzeria.pizzeriawebapp.dtos.PizzaDTO updatePizza(Long id, Pizza pizza);

    void deletePizza(Long id);
}