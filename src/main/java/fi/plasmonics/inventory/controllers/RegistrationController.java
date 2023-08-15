package fi.plasmonics.inventory.controllers;

import fi.plasmonics.inventory.entity.InventoryRole;
import fi.plasmonics.inventory.entity.InventoryUser;
import fi.plasmonics.inventory.entity.InventoryUserAccount;
import fi.plasmonics.inventory.model.request.registration.RegisterUser;
import fi.plasmonics.inventory.model.response.MessageType;
import fi.plasmonics.inventory.services.InventoryUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;


@CrossOrigin
@RestController
@RequestMapping("/api/auth")
public class RegistrationController {

//    @Autowired
//    private InventoryUserService inventoryUserService;

//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//
//    @PostMapping("/register")
//    public MessageType registerInventoryUser(@RequestBody RegisterUser registerUser) {
//        InventoryUser inventoryUser = new InventoryUser();
//        inventoryUser.setFirstName(registerUser.getFirstName());
//        inventoryUser.setLastName(registerUser.getLastName());
//        inventoryUser.setEmail(registerUser.getEmail());
//        inventoryUser.setPhoneNumber(registerUser.getPhoneNumber());
//        inventoryUser.setCreatedBy("ADMIN");
//        inventoryUser.setCreateTime(Timestamp.from(Instant.now()));
//        InventoryUserAccount inventoryUserAccount = new InventoryUserAccount();
//        inventoryUserAccount.setUserName(registerUser.getUserName());
//        inventoryUserAccount.setPassword(passwordEncoder.encode(registerUser.getPassword()));
//        inventoryUserAccount.setAccountNonExpired(true);
//        inventoryUserAccount.setAccountNonLocked(true);
//        inventoryUserAccount.setCredentialsNonExpired(true);
//        inventoryUserAccount.setEnabled(false);
//        InventoryRole inventoryRole = new InventoryRole();
//        inventoryRole.setRoleName(registerUser.getRole());
//        inventoryRole.setInventoryUserAccount(inventoryUserAccount);
//        inventoryUserAccount.setInventoryRole(inventoryRole);
//        inventoryUserAccount.setInventoryUser(inventoryUser);
//        inventoryUser.setInventoryUserAccount(inventoryUserAccount);
//         InventoryUser inventoryUser1 = inventoryUserService.save(inventoryUser);
//         if(inventoryUser1 != null){
//             return MessageType.SUCCESS;
//         }
//        return MessageType.FAILURE;
//    }
//
//
//    @PutMapping("/enable/{userId}")
//    public MessageType enableInventoryUser(@PathVariable String userId) {
//        Optional<InventoryUser> inventoryUserOptional = inventoryUserService.getInventoryUser(Long.parseLong(userId));
//        if(inventoryUserOptional.isPresent()){
//            InventoryUser inventoryUser= inventoryUserOptional.get();
//            inventoryUser.getInventoryUserAccount().setEnabled(true);
//            inventoryUserService.save(inventoryUser);
//            return MessageType.SUCCESS;
//        }else{
//            return MessageType.FAILURE;
//        }
//    }

}
