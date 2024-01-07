package com.example.demo.exception;

public class BookAlreadyExistException extends RuntimeException {
    public BookAlreadyExistException(String message){
        super(message);
    }
   public BookAlreadyExistException(String message, Throwable cause){
        super(message, cause);
   }
}
