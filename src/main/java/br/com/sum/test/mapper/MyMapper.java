package br.com.sum.test.mapper;

import br.com.sum.test.model.Person;
import br.com.sum.test.model.dto.v1.PersonDTO;
import org.modelmapper.ModelMapper;


public class MyMapper {

    public static ModelMapper mapper = new ModelMapper();


    static {
        mapper.createTypeMap(
                        Person.class,
                        PersonDTO.class)
                .addMapping(Person::getId, PersonDTO::setKey);
    }

}

