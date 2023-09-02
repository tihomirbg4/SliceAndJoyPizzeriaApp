package com.pizzeria.pizzeriawebapp.exceptions;

public class UserAlreadyExistsException  extends RuntimeException{

    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
