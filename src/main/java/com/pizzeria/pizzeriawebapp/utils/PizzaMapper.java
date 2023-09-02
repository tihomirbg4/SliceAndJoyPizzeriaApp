package com.pizzeria.pizzeriawebapp.utils;

import com.pizzeria.pizzeriawebapp.dtos.PizzaDTO;
import com.pizzeria.pizzeriawebapp.models.Pizza;

public class PizzaMapper {

    private PizzaMapper() {

    }

    public static com.pizzeria.pizzeriawebapp.dtos.PizzaDTO pizzaToDTO(Pizza pizza) {
        com.pizzeria.pizzeriawebapp.dtos.PizzaDTO pizzaDTO = new com.pizzeria.pizzeriawebapp.dtos.PizzaDTO();

        pizzaDTO.setId(pizza.getId());
        pizzaDTO.setName(pizza.getName());
        pizzaDTO.setSize(pizza.getSize());
        pizzaDTO.setPrice(pizza.getPrice());
        pizzaDTO.setProducts(pizza.getProducts());
        pizzaDTO.setDough(pizza.getDough());
        pizzaDTO.setImageUrl(pizza.getImageUrl());
        pizzaDTO.setQuantity(pizza.getQuantity());
        pizzaDTO.setAvailable(pizza.getIsAvailable());

        return pizzaDTO;
    }

    public static Pizza pizzaDTOToPizza(PizzaDTO pizzaDTO) {
        Pizza pizza = new Pizza();
        pizza.setId(pizzaDTO.getId());
        pizza.setName(pizzaDTO.getName());
        pizza.setProducts(pizzaDTO.getProducts());
        pizza.setDough(pizzaDTO.getDough());
        pizza.setSize(pizzaDTO.getSize());
        pizza.setImageUrl(pizzaDTO.getImageUrl());

        return pizza;
    }
}
