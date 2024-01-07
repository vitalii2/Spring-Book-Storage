package com.example.demo.repository;

import com.example.demo.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepo extends JpaRepository<Book, Long> {
    Optional<Book> findById(Long id);
    Optional<Book> findByTitle(String title);
    List<Book> findAll();
}
