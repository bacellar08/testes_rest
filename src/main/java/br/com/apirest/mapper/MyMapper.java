package br.com.apirest.mapper;

import br.com.apirest.model.Person;
import br.com.apirest.model.dto.v1.PersonDTO;
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

