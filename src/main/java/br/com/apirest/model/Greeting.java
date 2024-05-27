package br.com.apirest.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Greeting {

    private String name;
    private String message;

    public Greeting(String message, String name) {

        this.name = name;
        this.message = message;
    }


}
