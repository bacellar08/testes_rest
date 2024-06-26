package br.com.apirest.controller;

import br.com.apirest.model.Greeting;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    @GetMapping("/greet")
    public Greeting greet(
            @RequestParam(value="name",
                    defaultValue = "World") String name,

            @RequestParam(value="message",
                    defaultValue = "Hello, ") String message) {

            return new Greeting(message, name);

    }

}
