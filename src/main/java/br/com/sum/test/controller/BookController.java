package br.com.sum.test.controller;

import br.com.sum.test.model.Book;
import br.com.sum.test.model.dto.v1.BookDTO;
import br.com.sum.test.service.BookService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@Tag(name = "Books", description = "Endpoints for managing books.")
public class BookController {

    @Autowired
    BookService service;

    @GetMapping(value = "/{id}",
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
    )
    public ResponseEntity<BookDTO> findBookById(@PathVariable Integer id) {
       BookDTO book = service.getBook(id);
       return ResponseEntity.ok(book);
    }

    @GetMapping(
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
    )
    public List<BookDTO> findAllBooks() {
        return service.getAllBooks();
    }

    @PostMapping(
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
            consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
    )
    public ResponseEntity<BookDTO> create(@RequestBody BookDTO book) {
        BookDTO createdBook = service.createBook(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBook);
    }

    @PutMapping(
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
            consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
    )
    public ResponseEntity<BookDTO> updateBook(@RequestBody BookDTO book) {
        BookDTO updatedBook = service.updateBook(book);
        return ResponseEntity.ok(updatedBook);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Integer id) {
        service.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
