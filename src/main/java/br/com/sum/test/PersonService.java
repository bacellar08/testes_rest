package br.com.sum.test;

import br.com.sum.test.exceptions.ResourceNotFoundException;
import br.com.sum.test.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.logging.Logger;

@Service
public class PersonService {

    private Logger logger = Logger.getLogger(PersonService.class.getName());

    @Autowired
    PersonRepository repository;

    public List<Person> findAll() {

        logger.info("Finding all people");

        return repository.findAll();
    }

    public Person findById(Long id) {
        logger.info("Finding person by id: " + id);


        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Person with id: " + id + " was not found."));
    }

    public Person addPerson(Person person) {
        logger.info("Adding person: " + person);
        return repository.save(person);
    }

    public Person updatePerson(Person person) {
        logger.info("Updating person: " + person);

        repository.findById(person.getId()).
                orElseThrow(() -> new ResourceNotFoundException("Person with id: " + person.getId() + " was not found."));

        return repository.save(person);

    }

    public void deletePerson(Long id) {
        logger.info("Deleting person with id: " + id);
        var deletedPerson = repository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Person with id: " + id + " was not found."));
        repository.delete(deletedPerson);

    }
}
