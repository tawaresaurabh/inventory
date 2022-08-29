package fi.plasmonics.inventory.model.request.login;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginUser implements Serializable {
    private final String userName;
    private final String password;
}
