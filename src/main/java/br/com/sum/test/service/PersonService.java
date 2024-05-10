package br.com.sum.test.service;

import br.com.sum.test.controller.PersonController;
import br.com.sum.test.model.Person;
import br.com.sum.test.model.dto.v1.PersonDTO;
import br.com.sum.test.exceptions.ResourceNotFoundException;
import br.com.sum.test.repository.PersonRepository;
import org.modelmapper.ModelMapper;
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

    private static ModelMapper mapper = new ModelMapper();


    static {
        mapper.createTypeMap(
                        Person.class,
                        PersonDTO.class)
                .addMapping(Person::getId, PersonDTO::setKey);
    }

    public List<PersonDTO> findAll() {

        logger.info("Finding all people");

        return repository.findAll().stream().map(person -> {
            PersonDTO personDTO = mapper.map(person, PersonDTO.class);
            personDTO.add(linkTo(methodOn(PersonController.class).findById(personDTO.getKey())).withSelfRel());
            return personDTO;
        }).collect(Collectors.toList());
    }

    public PersonDTO findById(Long id) {
        logger.info("Finding person by id: " + id);


        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Person with id: " + id + " was not found."));

        var personDTO = mapper.map(entity, PersonDTO.class);

        personDTO.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());

        return personDTO;
    }

    public PersonDTO addPerson(PersonDTO person) {
        logger.info("Adding person: " + person);

        var entity = mapper.map(person, Person.class);

        var personDTO = mapper.map(repository.save(entity), PersonDTO.class);
        personDTO.add(linkTo(methodOn(PersonController.class).findById(personDTO.getKey())).withSelfRel());

        return personDTO;


    }

    public PersonDTO updatePerson(PersonDTO person) {
        logger.info("Updating person: " + person);

        var entity = repository.findById(person.getKey()).
                orElseThrow(() -> new ResourceNotFoundException("Person with id: " + person.getKey() + " was not found."));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        var personDTO = mapper.map(repository.save(entity), PersonDTO.class);
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
