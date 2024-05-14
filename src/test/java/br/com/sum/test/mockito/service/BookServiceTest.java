package br.com.sum.test.mockito.service;

import br.com.sum.test.model.Book;
import br.com.sum.test.model.Person;
import br.com.sum.test.model.dto.v1.BookDTO;
import br.com.sum.test.repository.BookRepository;
import br.com.sum.test.service.BookService;
import br.com.sum.test.unittest.mapper.MockBook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    MockBook input;

    @InjectMocks
    BookService service;

    @Mock
    BookRepository repository;

    @BeforeEach
    void setUp() {
        input = new MockBook();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getBook() {
        Book entity = input.mockEntity(1);
        entity.setId(1);

        when(repository.findById(1)).thenReturn(Optional.of(entity));
        var result = service.getBook(1);

        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertTrue(result.toString().contains("links: [</api/books/1>;rel=\"self\"]"));
        assertEquals("Title Test1", result.getTitle());
        assertEquals("Author Name Test1", result.getAuthor());
        assertEquals(1, result.getKey());
        assertEquals(new Date().getDay(), result.getLaunchDate().getDay());

    }

    @Test
    void getAllBooks() {
        List<Book> entityList = input.mockEntityList();
        when(repository.findAll()).thenReturn(entityList);
        var result = service.getAllBooks();

        assertNotNull(result);
        var count = 0;

        for (Book book : entityList) {
            assertNotNull(book);
            assertNotNull(book.getId());
            assertEquals("Title Test" + count, book.getTitle());
            assertEquals("Author Name Test" + count, book.getAuthor());
            assertEquals(count, book.getPrice());
            assertTrue(result.toString().contains("links: [</api/books/"+count+">;rel=\"self\"]"));

            count++;
        }
    }

    @Test
    void createBook() {
        Book entity = input.mockEntity(1);
        entity.setId(1);

        BookDTO dto = input.mockDTO(1);
        dto.setKey(1);

        when(repository.save(any())).thenReturn(entity);
        var result = service.createBook(dto);

        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertTrue(result.toString().contains("links: [</api/books/1>;rel=\"self\"]"));
        assertEquals("Title Test1", result.getTitle());
        assertEquals("Author Name Test1", result.getAuthor());
        assertEquals(1, result.getKey());
        assertEquals(new Date().getDay(), result.getLaunchDate().getDay());
    }

    @Test
    void updateBook() {
        Book entity = input.mockEntity(1);
        entity.setId(1);

        BookDTO dto = input.mockDTO(1);
        dto.setKey(1);

        when(repository.findById(1)).thenReturn(Optional.of(entity));
        when(repository.save(any())).thenReturn(entity);
        var result = service.updateBook(dto);

        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertTrue(result.toString().contains("links: [</api/books/1>;rel=\"self\"]"));
        assertEquals("Title Test1", result.getTitle());
        assertEquals("Author Test1", result.getAuthor());
        assertEquals(1, result.getKey());
        assertEquals(new Date().getDay(), result.getLaunchDate().getDay());
    }

    @Test
    void deleteBook() {
        Book entity = input.mockEntity(1);
        entity.setId(1);

        when(repository.findById(1)).thenReturn(Optional.of(entity));
        service.deleteBook(1);
    }
}