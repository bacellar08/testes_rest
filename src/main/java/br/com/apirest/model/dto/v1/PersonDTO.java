package br.com.apirest.model.dto.v1;



import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
public class PersonDTO extends RepresentationModel<PersonDTO> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;


    
    public Long key;
    public String firstName, lastName, address, gender;

    public PersonDTO() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PersonDTO personDTO = (PersonDTO) o;
        return Objects.equals(key, personDTO.key) && Objects.equals(firstName, personDTO.firstName) && Objects.equals(lastName, personDTO.lastName) && Objects.equals(address, personDTO.address) && Objects.equals(gender, personDTO.gender);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), key, firstName, lastName, address, gender);
    }
}

