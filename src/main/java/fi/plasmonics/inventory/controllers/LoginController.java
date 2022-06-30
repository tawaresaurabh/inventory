package fi.plasmonics.inventory.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class LoginController {


    @GetMapping("/login")
    public String getLoginAny() {
        return "Logged-in-any";
    }

    @GetMapping("/login-admin")
    public String getLoginAdmin() {
        return "Logged-in-admin";
    }

    @GetMapping("/login-user")
    public String getLoginUser() {
        return "Logged-in-user";
    }

}
