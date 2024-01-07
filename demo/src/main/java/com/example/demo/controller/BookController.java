package com.example.demo.controller;

import com.example.demo.entity.Book;
import com.example.demo.exception.BookNotFoundException;
import com.example.demo.service.BookService;
import com.example.demo.service.BookServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
@CrossOrigin(origins = "http://localhost:63342")
@AllArgsConstructor
@RequestMapping("/")
public class BookController {
    @Autowired
    private BookService bookService;
    private final BookServiceImpl bookServiceImpl;
    @GetMapping("/addLibrary.html")
    public String showLibraryPage(Model model){
        return "addLibrary";
    }
    @GetMapping("/welcome")
    public String welcomePage(Model model) {
        System.out.println("Success");
        return "welcome";
    }

    //    @GetMapping("Get/api/{bookId}")
//    public String getBook(@PathVariable String bookId, Model model) {
////        return bookService.findById(Long.valueOf(Integer.parseInt(bookId)));
//        ResponseEntity<Object> book = bookServiceImpl.findById(Long.parseLong(bookId));
//        model.addAttribute("book", book);
//       return "book";
//    }
    @GetMapping("Get/api/{bookId}")
    public String getBook(@PathVariable String bookId, Model model) {
        ResponseEntity<Book> responseEntity = bookServiceImpl.findById(Long.parseLong(bookId));

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            Book book = (Book) responseEntity.getBody();
            model.addAttribute("book", book);
            return "book";
        } else {

            throw new BookNotFoundException("We didn't find this book");
        }
    }

    @GetMapping("Get/api/books")
    public ModelAndView getAllBooks(Model model) {
        ResponseEntity<List<Book>> response = bookService.getBooks();
        if (response.getStatusCode().is2xxSuccessful()) {
            List<Book> books = (List<Book>) response.getBody();
            model.addAttribute("books", books);
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("allBooks");
            modelAndView.addAllObjects(model.asMap());
            return modelAndView;
        } else {
            throw new BookNotFoundException("We didn't find any books, maybe database is empty");
        }
    }

    @PostMapping("/save")
    public String addBook(@ModelAttribute Book book) {
        System.out.println("Success");
        bookService.save(book);
        return "redirect:/Get/api/books";
    }

    @PostMapping("Get/api/{bookId}")
    public String updateBookStatus(@PathVariable String bookId, @RequestParam String newStatus) {
        ResponseEntity<Object> response = bookService.updateStatus(Long.parseLong(bookId), newStatus);
        if (response.getStatusCode().is2xxSuccessful()) {
            return "redirect:/Get/api/" + bookId;
        } else {
            return "redirect:/error";
        }
    }

    @PostMapping("delete/api/books/{bookId}")
    public String delete(@PathVariable String bookId) {
        bookService.deleteBookById(Long.parseLong(bookId));
        return "redirect:/Get/api/books";
    }
}
