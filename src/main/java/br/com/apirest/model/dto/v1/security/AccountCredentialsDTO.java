package br.com.apirest.model.dto.v1.security;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
public class AccountCredentialsDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String username;
    private String password;
}
