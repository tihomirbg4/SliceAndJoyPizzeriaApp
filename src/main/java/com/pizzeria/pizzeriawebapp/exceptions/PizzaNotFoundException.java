package com.pizzeria.pizzeriawebapp.exceptions;

public class PizzaNotFoundException extends RuntimeException{

    public PizzaNotFoundException(String message) {
        super(message);
    }
}
