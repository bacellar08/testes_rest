package br.com.apirest.service;

import br.com.apirest.controller.PersonController;
import br.com.apirest.exceptions.RequiredObjectIsNullException;
import br.com.apirest.mapper.MyMapper;
import br.com.apirest.model.Person;
import br.com.apirest.model.dto.v1.PersonDTO;
import br.com.apirest.exceptions.ResourceNotFoundException;
import br.com.apirest.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class PersonService {

    private Logger logger = Logger.getLogger(PersonService.class.getName());

    @Autowired
    PersonRepository repository;

    public List<PersonDTO> findAll() {

        logger.info("Finding all people");

        return repository.findAll().stream().map(person -> {
            PersonDTO personDTO = MyMapper.mapper.map(person, PersonDTO.class);
            personDTO.add(linkTo(methodOn(PersonController.class).findById(personDTO.getKey())).withSelfRel());
            return personDTO;
        }).collect(Collectors.toList());
    }

    public PersonDTO findById(Long id) {
        logger.info("Finding person by id: " + id);


        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Person with id: " + id + " was not found."));

        var personDTO = MyMapper.mapper.map(entity, PersonDTO.class);

        personDTO.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());

        return personDTO;
    }

    public PersonDTO addPerson(PersonDTO person) {

        if (person == null) throw new RequiredObjectIsNullException();

        logger.info("Adding person: " + person);

        var entity = MyMapper.mapper.map(person, Person.class);

        var personDTO = MyMapper.mapper.map(repository.save(entity), PersonDTO.class);
        personDTO.add(linkTo(methodOn(PersonController.class).findById(personDTO.getKey())).withSelfRel());

        return personDTO;


    }

    public PersonDTO updatePerson(PersonDTO person) {

        if (person == null) throw new RequiredObjectIsNullException();

        logger.info("Updating person: " + person);

        var entity = repository.findById(person.getKey()).
                orElseThrow(() -> new ResourceNotFoundException("Person with id: " + person.getKey() + " was not found."));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        var personDTO = MyMapper.mapper.map(repository.save(entity), PersonDTO.class);
        personDTO.add(linkTo(methodOn(PersonController.class).findById(personDTO.getKey())).withSelfRel());
        return personDTO;

    }

    public void deletePerson(Long id) {
        logger.info("Deleting person with id: " + id);
        var deletedPerson = repository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Person with id: " + id + " was not found."));
        repository.delete(deletedPerson);

    }
}
