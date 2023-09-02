package com.pizzeria.pizzeriawebapp.exceptions;

import com.pizzeria.pizzeriawebapp.models.Cart;

public class CartItemNotFoundException extends RuntimeException{

    public CartItemNotFoundException(String message) {
        super(message);
    }
}
