package br.com.sum.test.unittest.mapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import br.com.sum.test.model.Book;
import br.com.sum.test.model.dto.v1.BookDTO;

public class MockBook {


    public Book mockEntity() {
        return mockEntity(0);
    }

    public BookDTO mockDTO() {
        return mockDTO(0);
    }

    public List<Book> mockEntityList() {
        List<Book> books = new ArrayList<Book>();
        for (int i = 0; i < 14; i++) {
            books.add(mockEntity(i));
        }
        return books    ;
    }

    public List<BookDTO> mockDTOList() {
        List<BookDTO> books = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            books.add(mockDTO(i));
        }
        return books;
    }

    public Book mockEntity(Integer number) {
        Book book = new Book();
        book.setTitle("Title Test" + number);
        book.setAuthor("Author Name Test" + number);
        book.setLaunchDate(new Date());
        book.setId(number);
        book.setPrice(number);
        return book;
    }

    public BookDTO mockDTO(Integer number) {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setTitle("Title Test" + number);
        bookDTO.setAuthor("Author Test" + number);
        bookDTO.setLaunchDate(new Date());
        bookDTO.setKey(number);
        bookDTO.setPrice(number);
        return bookDTO;
    }

}
