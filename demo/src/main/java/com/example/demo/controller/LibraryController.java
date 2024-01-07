package com.example.demo.controller;

import com.example.demo.entity.Book;
import com.example.demo.entity.Library;
import com.example.demo.exception.BookNotFoundException;
import com.example.demo.exception.LibraryNotExist;
import com.example.demo.service.LibraryService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/library")
public class LibraryController {
    LibraryService libraryService;

    @GetMapping("get/alllibraries")
    public ModelAndView getAllLibraries(Model model) {
        ResponseEntity<List<Library>> response = libraryService.getLibraris();
        if (response.getStatusCode().is2xxSuccessful()) {
            List<Library> libraries = (List<Library>) response.getBody();
            model.addAttribute("libraries", libraries);
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("allLibrarys");
            modelAndView.addAllObjects(model.asMap());
            return modelAndView;
        } else {
            throw new LibraryNotExist("We didn't find any libraries, maybe database is empty");
        }
    }
    @GetMapping("/{libraryId}")
    public String getLibrary(@PathVariable String libraryId,Model model){
    ResponseEntity<Object> response = libraryService.findById(Long.valueOf(Integer.parseInt(libraryId)));
    if (response.getStatusCode().is2xxSuccessful()){
        Library library = (Library) response.getBody();
        model.addAttribute("library",library);
        return "getLibrary";
    }else
        throw new LibraryNotExist("Library not found with ID: " + libraryId);
    }
    @PostMapping(value = "/saveLibrary", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String addLibrary(@ModelAttribute Library library){
        libraryService.save(library);
        return "redirect:/library/get/alllibraries";
    }
    @GetMapping("/getLibrary/name/{name}")
    public ResponseEntity<Object> getLibraryByName(@PathVariable String name){
        return libraryService.findByNameIgnoreCase(name);
    }
    @GetMapping("/getLibrary/address/{address}")
    public ResponseEntity<Object> getLibraryByAddress(@PathVariable String address){
        return libraryService.findByAdress(address);
    }
}
