package com.ninaja.todoapi.exceptions.models;

public class ExistingEmailException extends RuntimeException {
    
    public ExistingEmailException(String existingEmail){
        super(existingEmail + " email existing in the system");
    }
}