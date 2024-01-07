package com.example.demo.exception;

public class LibraryNotExist extends RuntimeException{
    public LibraryNotExist(String message){
        super(message);
    }
    public LibraryNotExist(String message, Throwable cause){
        super(message, cause);
    }
}
