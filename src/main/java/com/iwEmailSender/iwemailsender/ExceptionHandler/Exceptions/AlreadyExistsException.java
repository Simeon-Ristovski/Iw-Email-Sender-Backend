package com.iwEmailSender.iwemailsender.ExceptionHandler.Exceptions;

public class AlreadyExistsException extends RuntimeException{
    public AlreadyExistsException(String message) {
        super(message);
    }
}
