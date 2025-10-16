package com.iwEmailSender.iwemailsender.ExceptionHandler.Exceptions;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException (String string){
        super(string);
    }
}
