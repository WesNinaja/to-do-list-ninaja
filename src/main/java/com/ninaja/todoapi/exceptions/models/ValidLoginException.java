package com.ninaja.todoapi.exceptions.models;

public class ValidLoginException extends RuntimeException {
    
    public ValidLoginException(){
        super("Email or password field is wrong");
    }
}