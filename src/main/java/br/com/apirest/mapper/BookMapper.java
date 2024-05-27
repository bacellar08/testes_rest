package br.com.apirest.mapper;

import br.com.apirest.model.Book;
import br.com.apirest.model.dto.v1.BookDTO;
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
