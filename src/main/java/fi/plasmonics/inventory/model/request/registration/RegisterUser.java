package fi.plasmonics.inventory.model.request.registration;

import java.io.Serializable;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegisterUser implements Serializable {

    private final String firstName;
    private final String lastName;
    private final String userName;
    private final String password;
    private final String email;
    private final String phoneNumber;
    private final Set<String> roles;

}
