package br.com.sum.test.service;

import br.com.sum.test.model.Person;
import br.com.sum.test.model.v1.PersonDTO;
import br.com.sum.test.exceptions.ResourceNotFoundException;
import br.com.sum.test.mapper.MyMapper;
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

    public List<PersonDTO> findAll() {

        logger.info("Finding all people");

        return MyMapper.parseListObjects(repository.findAll(), PersonDTO.class);
    }

    public PersonDTO findById(Long id) {
        logger.info("Finding person by id: " + id);


        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Person with id: " + id + " was not found."));

        return MyMapper.parseObject(entity, PersonDTO.class);
    }

    public PersonDTO addPerson(PersonDTO person) {
        logger.info("Adding person: " + person);

        var entity = MyMapper.parseObject(person, Person.class);

        return  MyMapper.parseObject(repository.save(entity), PersonDTO.class);
    }

    public PersonDTO updatePerson(PersonDTO person) {
        logger.info("Updating person: " + person);

        var entity = repository.findById(person.getId()).
                orElseThrow(() -> new ResourceNotFoundException("Person with id: " + person.getId() + " was not found."));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        return MyMapper.parseObject(repository.save(entity), PersonDTO.class);

    }

    public void deletePerson(Long id) {
        logger.info("Deleting person with id: " + id);
        var deletedPerson = repository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Person with id: " + id + " was not found."));
        repository.delete(deletedPerson);

    }
}
