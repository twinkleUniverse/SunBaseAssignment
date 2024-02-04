package com.example.SunBase.exception;

public class CustomerAlreadyExist extends RuntimeException{
    public CustomerAlreadyExist(String msg){
        super(msg);
    }
}
