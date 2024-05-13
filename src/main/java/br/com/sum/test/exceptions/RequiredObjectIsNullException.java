package br.com.sum.test.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RequiredObjectIsNullException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;


    public RequiredObjectIsNullException() {

        super("Required object is null!");
    }


}
