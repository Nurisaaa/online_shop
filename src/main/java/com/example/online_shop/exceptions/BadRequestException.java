package com.example.online_shop.exceptions;

public class BadRequestException extends RuntimeException{
    public BadRequestException(String massage){
        super(massage);
    }
}