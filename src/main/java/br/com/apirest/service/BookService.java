package br.com.apirest.service;

import br.com.apirest.controller.BookController;
import br.com.apirest.mapper.BookMapper;
import br.com.apirest.model.dto.v1.BookDTO;
import br.com.apirest.exceptions.RequiredObjectIsNullException;
import br.com.apirest.exceptions.ResourceNotFoundException;
import br.com.apirest.model.Book;
import br.com.apirest.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;



@Service
public class BookService {

    @Autowired
    BookRepository repository;

    private Logger logger = Logger.getLogger(BookService.class.getName());

    public BookDTO getBook(Integer id) {

        logger.info("Finding book by id: " + id);

       var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book not found!"));

       var bookDTO = BookMapper.mapper.map(entity, BookDTO.class);

       return bookDTO.add(WebMvcLinkBuilder.linkTo(methodOn(BookController.class).findBookById(bookDTO.getKey())).withSelfRel());
    }

    public List<BookDTO> getAllBooks() {
        logger.info("Finding all books");

        return repository.findAll().stream().map(book -> {
            BookDTO bookDTO = BookMapper.mapper.map(book, BookDTO.class);
            return bookDTO.add(linkTo(methodOn(BookController.class).findBookById(bookDTO.getKey())).withSelfRel());
        }).collect(Collectors.toList());
    }

    public BookDTO createBook(BookDTO book) {
        logger.info("Creating book: " + book);
        if (book == null) throw new RequiredObjectIsNullException();

        var entity = BookMapper.mapper.map(book, Book.class);

        var bookDTO = BookMapper.mapper.map(repository.save(entity), BookDTO.class);
        bookDTO.add(linkTo(methodOn(BookController.class).findBookById(bookDTO.getKey())).withSelfRel());


        return bookDTO;
    }

    public BookDTO updateBook(BookDTO book) {
        logger.info("Updating book: " + book);
        if (book == null) throw new RequiredObjectIsNullException();

        var target = repository.findById(book.getKey()).orElseThrow(() -> new ResourceNotFoundException("Book not found!"));

        target.setTitle(book.getTitle());
        target.setAuthor(book.getAuthor());
        target.setPrice(book.getPrice());
        target.setLaunchDate(book.getLaunchDate());

        BookDTO bookDTO = BookMapper.mapper.map(repository.save(target), BookDTO.class);
        bookDTO.add(linkTo(methodOn(BookController.class).findBookById(bookDTO.getKey())).withSelfRel());
        return bookDTO;

    }

    public void deleteBook(Integer id) {
        logger.info("Deleting book: " + id);
        if (id == null) throw new RequiredObjectIsNullException();
        var target = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book not found!"));
        repository.deleteById(target.getId());
    }


}
