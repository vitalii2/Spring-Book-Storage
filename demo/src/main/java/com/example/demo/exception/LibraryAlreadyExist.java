package com.example.demo.exception;

public class LibraryAlreadyExist extends RuntimeException{
    public LibraryAlreadyExist(String message){
        super("This library already exist");
    }
    public LibraryAlreadyExist(String message, Throwable cause){
        super(message, cause);
    }
}
