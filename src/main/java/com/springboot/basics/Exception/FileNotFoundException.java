package com.springboot.basics.Exception;

public class FileNotFoundException extends RuntimeException {

    public FileNotFoundException(String message){
        super(message);
    }
}
