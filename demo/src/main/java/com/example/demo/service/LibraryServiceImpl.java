package com.example.demo.service;

import com.example.demo.entity.Library;
import com.example.demo.exception.LibraryAlreadyExist;
import com.example.demo.repository.LibraryRepo;
import com.example.demo.response.ResponseHandler;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor

public class LibraryServiceImpl implements LibraryService{
    private final LibraryRepo libraryRepo;
    @Override
    public ResponseEntity<Object> save(Library library) {
        Optional<Library> existingLibrary = libraryRepo.findByNameIgnoreCase(library.getName());
        if (existingLibrary.isPresent() && library.getName().equals(existingLibrary.get().getName())) {
            throw new LibraryAlreadyExist("This library already exists");
        }

        long id = libraryRepo.save(library).getId();
        return ResponseHandler.responseBuilder("library was saved", HttpStatus.OK, libraryRepo.findById(id));
    }

    @Override
    public ResponseEntity<Object> findById(Long id) {
        Library library = libraryRepo.findById(id).orElseThrow(() -> new LibraryAlreadyExist("Library does not exist"));

        return ResponseEntity.ok(library);
    }

    @Override
    public ResponseEntity<Object> findByNameIgnoreCase(String name) {
        if(!libraryRepo.findByNameIgnoreCase(name).isPresent()){
            throw new LibraryAlreadyExist("Library does not exist");
        }
        return ResponseHandler.responseBuilder("searched library", HttpStatus.OK, name);
    }


    @Override
    public ResponseEntity<Object> findByAdress(String address) {
        if(!libraryRepo.findByLocation(address).isPresent()){
            throw new LibraryAlreadyExist("Library does not exist");
        }
        return ResponseHandler.responseBuilder("searched library", HttpStatus.OK, address);
    }
    @Override
    public ResponseEntity<List<Library>> getLibraris(){
        List<Library> libraries = libraryRepo.findAll();
        return new ResponseEntity<>(libraries,HttpStatus.OK);
    }
}
