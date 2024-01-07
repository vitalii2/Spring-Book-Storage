package com.example.demo.repository;

import com.example.demo.entity.Library;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LibraryRepo extends JpaRepository<Library, Long> {
    Optional<Library> findById(Long id);
    Optional<Library> findByNameIgnoreCase(String name);
    Optional<Library> findByLocation(String location);
}
