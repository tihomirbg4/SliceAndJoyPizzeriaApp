package com.pizzeria.pizzeriawebapp.exceptions;

public class PasswordDoNotMatchException extends RuntimeException{

    public PasswordDoNotMatchException(String message) {
        super(message);
    }
}
