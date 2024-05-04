package br.com.sum.test;

import br.com.sum.test.dto.v1.PersonDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    PersonService service;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PersonDTO findById(@PathVariable("id")Long id){
        return service.findById(id);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PersonDTO> findAll(){
        return service.findAll();
    }

    @PostMapping
    public ResponseEntity<PersonDTO> create(@RequestBody PersonDTO person){
        PersonDTO createdPerson = service.addPerson(person);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPerson);
    }

    @PutMapping
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
