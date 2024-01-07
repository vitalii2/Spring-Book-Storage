package com.example.demo.service;

import com.example.demo.entity.Library;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface LibraryService {
    ResponseEntity<Object> save(Library library);
    ResponseEntity<Object> findById(Long id);
    ResponseEntity<Object> findByNameIgnoreCase(String name);
    ResponseEntity<List<Library>> getLibraris();
    ResponseEntity<Object> findByAdress(String adress);
}
