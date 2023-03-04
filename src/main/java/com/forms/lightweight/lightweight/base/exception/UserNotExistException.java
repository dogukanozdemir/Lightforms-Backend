package com.forms.lightweight.lightweight.base.exception;

public class UserNotExistException extends RuntimeException{

    public UserNotExistException(String message){
        super(message);
    }
}
