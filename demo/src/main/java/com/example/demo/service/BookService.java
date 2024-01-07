package com.example.demo.service;

import com.example.demo.entity.Book;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BookService {
    ResponseEntity<Object> save(Book book);

   ResponseEntity<List<Book>> getBooks();

    ResponseEntity<Book> findById(Long id);
    ResponseEntity<Object> findByName(String name);

    ResponseEntity<Object> updateStatus(Long bookId, String newStatus);

    public void deleteBookById(Long id);
}
