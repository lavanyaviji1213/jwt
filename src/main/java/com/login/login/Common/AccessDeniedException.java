package com.login.login.Common;

public class AccessDeniedException extends RuntimeException{
    public AccessDeniedException(String message){
        super(message);
    }
}
