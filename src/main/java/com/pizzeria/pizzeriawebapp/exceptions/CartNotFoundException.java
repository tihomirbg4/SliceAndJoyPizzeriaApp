package com.pizzeria.pizzeriawebapp.exceptions;

public class CartNotFoundException extends RuntimeException{

    public CartNotFoundException(String message) {
        super(message);
    }
}
