package com.pizzeria.pizzeriawebapp.exceptions;

public class InvalidCartItemException extends RuntimeException{

    public InvalidCartItemException(String message) {
        super(message);
    }
}
