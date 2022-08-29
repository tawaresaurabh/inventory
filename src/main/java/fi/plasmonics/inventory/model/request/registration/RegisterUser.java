package fi.plasmonics.inventory.model.request.registration;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class RegisterUser implements Serializable {

    private final String firstName;
    private final String lastName;
    private final String userName;
    private final String password;
    private final String email;
    private final String phoneNumber;
    private final String role;

}
