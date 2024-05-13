package br.com.sum.test.controller;

import br.com.sum.test.model.dto.v1.PersonDTO;
import br.com.sum.test.mockito.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/person/v1")
public class PersonController {

    @Autowired
    PersonService service;

    @GetMapping(value = "/{id}",
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })

    public PersonDTO findById(@PathVariable("id")Long id){
        return service.findById(id);
    }

    @GetMapping(
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public List<PersonDTO> findAll(){
        return service.findAll();
    }

    @PostMapping(value = "/create",
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
            consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<PersonDTO> create(@RequestBody PersonDTO person) {
        PersonDTO createdPerson = service.addPerson(person);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPerson);
    }

    @PutMapping(
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
            consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
    )
    public ResponseEntity<PersonDTO> update(@RequestBody PersonDTO person){
        PersonDTO updatedPerson = service.updatePerson(person);
        return ResponseEntity.ok(updatedPerson);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable(value="id") Long id){
        service.deletePerson(id);
        return ResponseEntity.noContent().build();
    }
}
