package fi.plasmonics.inventory.model.response.auth;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JWTResponse implements Serializable {
    private final String token;
    private final String userName;
    private final List<String> roles;
}
