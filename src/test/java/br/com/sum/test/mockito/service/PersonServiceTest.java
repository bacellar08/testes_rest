package br.com.sum.test.mockito.service;

import br.com.sum.test.exceptions.RequiredObjectIsNullException;
import br.com.sum.test.model.Person;
import br.com.sum.test.model.dto.v1.PersonDTO;
import br.com.sum.test.repository.PersonRepository;
import br.com.sum.test.unittest.mapper.MockPerson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class PersonServiceTest {


    MockPerson input;

    @InjectMocks
    PersonService service;

    @Mock
    PersonRepository repository;



    @BeforeEach
    void setUp() {
        input = new MockPerson();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findById() {
        Person entity = input.mockEntity(1);
        entity.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        var result = service.findById(1L);

        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertTrue(result.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));
        assertEquals("Address Test1", result.getAddress());
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Female", result.getGender());

    }

    @Test
    void findAll() {

        List<Person> personList = input.mockEntityList();
        when(repository.findAll()).thenReturn(personList);
        var result = service.findAll();

        assertNotNull(result);
        var count = 0;

        for (Person person : personList) {
            var gender = ((count % 2)==0) ? "Male" : "Female";
            assertNotNull(person);
            assertNotNull(person.getId());
            assertEquals("First Name Test" + count, person.getFirstName());
            assertEquals("Last Name Test" + count, person.getLastName());
            assertEquals("Address Test" + count, person.getAddress());
            assertEquals(gender, person.getGender());
            assertTrue(result.toString().contains("links: [</api/person/v1/"+count+">;rel=\"self\"]"));

            count++;
        }


    };

    @Test
    void addPerson() {
        Person entity = input.mockEntity(1);
        entity.setId(1L);

        PersonDTO dto = input.mockDTO(1);
        dto.setKey(1L);

        when(repository.save(any())).thenReturn(entity);

        var result = service.addPerson(dto);

        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertTrue(result.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));
        assertEquals("Address Test1", result.getAddress());
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Female", result.getGender());

    }

    @Test
    void createNullPerson() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
            service.addPerson(null);
        });

        String expectedMessage = "Required object is null!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void updatePerson() {

        Person entity = input.mockEntity(1);
        entity.setId(1L);

        PersonDTO dto = input.mockDTO(1);
        dto.setKey(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(repository.save(any())).thenReturn(entity);

        var result = service.updatePerson(dto);

        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertTrue(result.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));
        assertEquals("Address Test1", result.getAddress());
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Female", result.getGender());

    }

    @Test
    void updateNullPerson() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
            service.updatePerson(null);
        });

        String expectedMessage = "Required object is null!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void deletePerson() {

        Person entity = input.mockEntity(1);
        entity.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        service.deletePerson(1L);
    }
}