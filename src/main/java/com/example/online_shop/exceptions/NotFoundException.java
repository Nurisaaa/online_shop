package com.example.online_shop.exceptions;

public class  NotFoundException extends RuntimeException{
    public NotFoundException(String massage){
        super(massage);
    }
}