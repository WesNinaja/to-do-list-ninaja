package com.ninaja.todoapi.exceptions.models;

public class ErrorInRegistrationException extends RuntimeException {
    
    public ErrorInRegistrationException(){
        super("Invalid name, email or password");
    }
}