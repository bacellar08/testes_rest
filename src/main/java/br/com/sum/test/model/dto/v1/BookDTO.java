package br.com.sum.test.model.dto.v1;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;


@Data
@EqualsAndHashCode(callSuper = true)
public class BookDTO extends RepresentationModel<BookDTO> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Integer key;
    private String title;
    private String author;
    private Date launchDate;
    private double price;


}
