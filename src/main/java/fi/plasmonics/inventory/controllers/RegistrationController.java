package fi.plasmonics.inventory.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

import fi.plasmonics.inventory.entity.InventoryUser;
import fi.plasmonics.inventory.entity.InventoryUserAccount;
import fi.plasmonics.inventory.model.request.registration.RegisterUser;
import fi.plasmonics.inventory.model.response.MessageType;
import fi.plasmonics.inventory.services.InventoryUserService;


@CrossOrigin
@RestController
@RequestMapping("/api")
public class RegistrationController {

    @Autowired
    private InventoryUserService inventoryUserService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostMapping("/register")
    public MessageType registerInventoryUser(@RequestBody RegisterUser registerUser) {
        InventoryUser inventoryUser = new InventoryUser();
        inventoryUser.setFirstName(registerUser.getFirstName());
        inventoryUser.setLastName(registerUser.getLastName());
        inventoryUser.setEmail(registerUser.getEmail());
        inventoryUser.setPhoneNumber(registerUser.getPhoneNumber());
        inventoryUser.setCreatedBy("ADMIN");
        inventoryUser.setCreateTime(Timestamp.from(Instant.now()));
        InventoryUserAccount inventoryUserAccount = new InventoryUserAccount();
        inventoryUserAccount.setUserName(registerUser.getUserName());
        inventoryUserAccount.setPassword(passwordEncoder.encode(registerUser.getPassword()));
        inventoryUserAccount.setAccountNonExpired(true);
        inventoryUserAccount.setAccountNonLocked(true);
        inventoryUserAccount.setCredentialsNonExpired(true);
        inventoryUserAccount.setEnabled(false);
        inventoryUserAccount.setInventoryUser(inventoryUser);
        inventoryUser.setInventoryUserAccount(inventoryUserAccount);
         InventoryUser inventoryUser1 = inventoryUserService.save(inventoryUser);
         if(inventoryUser1 != null){
             return MessageType.SUCCESS;
         }
        return MessageType.FAILURE;
    }


    @PutMapping("/enable/{userId}")
    public MessageType enableInventoryUser(@PathVariable String userId) {
        Optional<InventoryUser>inventoryUserOptional = inventoryUserService.getInventoryUser(Long.parseLong(userId));
        if(inventoryUserOptional.isPresent()){
            InventoryUser inventoryUser= inventoryUserOptional.get();
            inventoryUser.getInventoryUserAccount().setEnabled(true);
            inventoryUserService.save(inventoryUser);
            return MessageType.SUCCESS;
        }else{
            return MessageType.FAILURE;
        }
    }

}
