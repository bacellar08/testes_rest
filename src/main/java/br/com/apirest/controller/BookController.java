package br.com.apirest.controller;

import br.com.apirest.model.Book;
import br.com.apirest.model.dto.v1.BookDTO;
import br.com.apirest.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(
            summary = "Find book by ID",
            description = "This endpoint allows you to find any registered book by its ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success finding book by the specified ID",
                    content = @Content(schema = @Schema(implementation = Book.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Book with the specified ID was not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping(value = "/{id}",
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
    )
    public ResponseEntity<BookDTO> findBookById(@PathVariable Integer id) {
       BookDTO book = service.getBook(id);
       return ResponseEntity.ok(book);
    }

    @Operation(
            summary = "Find all books",
            description = "This endpoint allows you to find every book registered in the database"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success finding all books",
                    content = @Content(schema = @Schema(implementation = Book.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping(
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
    )
    public List<BookDTO> findAllBooks() {
        return service.getAllBooks();
    }

    @Operation(
            summary = "Register new book",
            description = "This endpoint allows you to register a new book in the database"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success registering new book",
                    content = @Content(schema = @Schema(implementation = Book.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PostMapping(
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
            consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
    )
    public ResponseEntity<BookDTO> create(@RequestBody BookDTO book) {
        BookDTO createdBook = service.createBook(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBook);
    }

    @Operation(
            summary = "Update book info",
            description = "This endpoint allows you to update already registered data of any book on the database"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success updating book",
                    content = @Content(schema = @Schema(implementation = Book.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "Book with the specified ID was not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PutMapping(
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
            consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
    )
    public ResponseEntity<BookDTO> updateBook(@RequestBody BookDTO book) {
        BookDTO updatedBook = service.updateBook(book);
        return ResponseEntity.ok(updatedBook);
    }

    @Operation(
            summary = "Delete book",
            description = "This endpoint allows you to delete any book by it's ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success deleting book",
                    content = @Content(schema = @Schema(implementation = Book.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Book with the specified ID was not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Integer id) {
        service.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
