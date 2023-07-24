package com.example.online_shop.exceptions;

public class BadCredentialException extends RuntimeException{
    public BadCredentialException(String massage){
        super(massage);
    }
}