package br.com.sum.test.mapper;

import br.com.sum.test.model.Book;
import br.com.sum.test.model.dto.v1.BookDTO;
import org.modelmapper.ModelMapper;

public class BookMapper {

    public static ModelMapper mapper = new ModelMapper();


    static {
        mapper.createTypeMap(
                        Book.class,
                        BookDTO.class)
                .addMapping(Book::getId, BookDTO::setKey);
    }
}
