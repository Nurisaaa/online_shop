package com.example.online_shop.exceptions;

public class AlreadyExistException extends RuntimeException{
    public AlreadyExistException(String massage){
        super(massage);
    }
}