package com.pizzeria.pizzeriawebapp.bootstrap;

import com.pizzeria.pizzeriawebapp.models.Pizza;

public class ParsedPizzaMapper {
    public static Pizza toPizza(ParsedPizza parsedPizza) {
        Pizza pizza = new Pizza();

        pizza.setName(parsedPizza.getName());
        pizza.setSize(parsedPizza.getSize());
        pizza.setDough(parsedPizza.getDough());
        pizza.setPrice(parsedPizza.getPrice());
        pizza.setProducts(parsedPizza.getProducts());
        pizza.setImageUrl(parsedPizza.getImageUrl());
        pizza.setQuantity(parsedPizza.getQuantity());
        pizza.setIsAvailable(parsedPizza.getIsAvailable());

        return pizza;
    }
}
