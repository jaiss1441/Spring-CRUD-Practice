package com.example.Spring_CRUD_Practice.controllers;

import com.example.Spring_CRUD_Practice.model.Book;
import com.example.Spring_CRUD_Practice.repo.BookRepo;
import com.example.Spring_CRUD_Practice.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepo bookRepo;

    @GetMapping("/getAllBooks")
    public ResponseEntity<List<Book>> getAllBook(){
        try {
            List<Book> bookList = bookService.getAllBooks();

            if(bookList.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(bookList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/getBookById/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id){
        Optional<Book> bookData = bookRepo.findById(id);

        if(bookData.isPresent()){
            return new ResponseEntity<>(bookData.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @PostMapping("/addBook")
    public ResponseEntity<Book> addBook(@RequestBody Book book){

        Book bookobj = bookRepo.save(book);

        return new ResponseEntity<>(bookobj, HttpStatus.OK);
    }

    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable Long id){

        bookRepo.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/updateById/{id}")
    public ResponseEntity<Book> updateById(@PathVariable Long id, @RequestBody Book newBookData){

        Optional<Book> oldBookData= bookRepo.findById(id);

        if(oldBookData.isPresent()){
           Book updatedBookData = oldBookData.get();

           updatedBookData.setTitle(newBookData.getTitle());
           updatedBookData.setAuthor(newBookData.getAuthor());

           Book bookObj =bookRepo.save(updatedBookData);
           return new ResponseEntity<>(bookObj, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}
