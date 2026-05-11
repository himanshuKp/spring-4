package com.himanshu.springpractice.controller;

import com.himanshu.springpractice.model.Book;
import com.himanshu.springpractice.record.BookResponseDTO;
import com.himanshu.springpractice.service.LibraryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.HtmlUtils;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.web.servlet.function.RequestPredicates.path;

@RestController
@RequestMapping("/api")
class LibraryController {

    private static final Logger logger = LoggerFactory.getLogger(LibraryController.class);

    @Autowired
    private LibraryService libraryService;

    @GetMapping("/books")
    public ResponseEntity<List<Book>> getAllBooks(){
        List<Book> books = libraryService.getBooks();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id){
        Optional<Book> book = libraryService.getBookById(id);
        logger.info("The book returned: "+book);
        return book.map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
    }

    @PostMapping("/books")
    public ResponseEntity<Map<String, Long>> addBook(@RequestBody Book book){
        Book savedBook = libraryService.addBook(book);
        logger.info("The book was added");

        Map<String, Long> response = Collections.singletonMap("id", savedBook.getId());

        URI location = UriComponentsBuilder.fromPath("/api/books/{id}")
                .buildAndExpand(savedBook.getId()).toUri();

        return ResponseEntity.created(location).contentType(MediaType.APPLICATION_JSON).body(response);
    }
}
