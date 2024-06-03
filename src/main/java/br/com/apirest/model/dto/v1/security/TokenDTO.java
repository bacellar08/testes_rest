package br.com.apirest.model.dto.v1.security;


import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class TokenDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String username;
    private Boolean authenticated;
    private Date creationDate;
    private Date expirationDate;
    private String accessToken;
    private String refreshToken;
}
