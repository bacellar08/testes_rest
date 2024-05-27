package br.com.apirest.model.dto.v1;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;



@Getter
@Setter
public class BookDTO extends RepresentationModel<BookDTO> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Integer key;
    private String title;
    private String author;
    private Date launchDate;
    private double price;


}
