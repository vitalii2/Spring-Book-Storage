package com.example.demo.service;

import com.example.demo.entity.Book;
import com.example.demo.exception.BookAlreadyExistException;
import com.example.demo.exception.BookNotFoundException;
import com.example.demo.repository.BookRepo;
import com.example.demo.response.ResponseHandler;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService{
    @Autowired
    private final BookRepo bookRepo;
//    @Override
//    public ResponseEntity<Object> save(Book book) {
//        Optional<Book> existingBook = bookRepo.findByTitle(book.getTitle());
//        if (existingBook.isPresent()){
//            throw new BookAlreadyExistException("This book already exist");
//        }
//        return ResponseHandler.responseBuilder("book was save", HttpStatus.OK, book.getId());
//    }
@Override
public ResponseEntity<Object> save(Book book) {
    Optional<Book> existingBook = bookRepo.findByTitle(book.getTitle());
    if (existingBook.isPresent()){
        throw new BookAlreadyExistException("This book already exists");
    }

    Book savedBook = bookRepo.save(book);
    return ResponseHandler.responseBuilder("Book was saved", HttpStatus.OK, savedBook.getId());
}
    @Override
    public ResponseEntity<List<Book>> getBooks(){
    List<Book> books = bookRepo.findAll();
    return new ResponseEntity<>(books,HttpStatus.OK);
    }

//    @Override
//    public ResponseEntity<Object> findById(Long id) {
//        if(!bookRepo.findById(id).isPresent())
//            throw new BookNotFoundException("This book does not exist");
//        return ResponseHandler.responseBuilder("searched book", HttpStatus.OK, bookRepo.findById(id).get());
//    }
@Override
public ResponseEntity<Book> findById(Long bookId) {
    Optional<Book> optionalBook = bookRepo.findById(bookId);
    return optionalBook.map(book -> new ResponseEntity<>(book, HttpStatus.OK))
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
}
    @Override
    public ResponseEntity<Object> findByName(String name) {
        if(!bookRepo.findByTitle(name).isPresent()){
            throw new BookNotFoundException("This book does not exist");
        }
        return ResponseHandler.responseBuilder("searched book", HttpStatus.OK, bookRepo.findByTitle(name).get());
    }


    @Override
    public ResponseEntity<Object> updateStatus(Long bookId, String newStatus) {
        Optional<Book> optionalBook = bookRepo.findById(bookId);

        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            book.setStatus(newStatus);
            bookRepo.save(book);
            return ResponseHandler.responseBuilder("Book status updated successfully.", HttpStatus.OK, null);
        } else {
            return ResponseHandler.responseBuilder("Book not found.", HttpStatus.NOT_FOUND,null);

    }
}

    @Override
    public void deleteBookById(Long id) {
        bookRepo.deleteById(id);
    }
}
