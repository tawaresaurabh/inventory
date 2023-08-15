package fi.plasmonics.inventory.setup;

import fi.plasmonics.inventory.entity.InventoryRole;
import fi.plasmonics.inventory.entity.InventoryUser;
import fi.plasmonics.inventory.entity.InventoryUserAccount;
import fi.plasmonics.inventory.repo.InventoryUserAccountRepository;
import fi.plasmonics.inventory.services.InventoryRoleService;
import fi.plasmonics.inventory.services.InventoryUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

@Component
@Profile("local")
public class SetupUserDataLoader implements ApplicationListener<ContextRefreshedEvent> {


    boolean alreadySetup = false;

    @Autowired
    private InventoryUserService inventoryUserService;

    @Autowired
    private InventoryUserAccountRepository inventoryUserAccountRepository;


    @Autowired
    private InventoryRoleService inventoryRoleService;


//    @Autowired
//    private PasswordEncoder passwordEncoder;

    Optional<InventoryRole> adminRole;
    Optional<InventoryRole> managerRole;
    Optional<InventoryRole> viewerRole;


    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

    }


//    @Transactional
//    void createUserIfNotFound(String name) {
//        Optional<InventoryUserAccount> inventoryUserAccountOptional = inventoryUserAccountRepository.findByUserName(name+"@"+name+".COM");
//        if(inventoryUserAccountOptional.isEmpty()){
//            InventoryUser inventoryUser = new InventoryUser();
//            inventoryUser.setFirstName(name);
//            inventoryUser.setLastName(name);
//            inventoryUser.setEmail(name+"@"+name+".COM");
//            inventoryUser.setPhoneNumber("0000000000");
//            inventoryUser.setCreatedBy(name);
//            inventoryUser.setCreateTime(Timestamp.from(Instant.now()));
//            InventoryUserAccount inventoryUserAccount = new InventoryUserAccount();
//            inventoryUserAccount.setUserName(name+"@"+name+".COM");
//            inventoryUserAccount.setPassword(passwordEncoder.encode(name));
//            inventoryUserAccount.setAccountNonExpired(true);
//            inventoryUserAccount.setAccountNonLocked(true);
//            inventoryUserAccount.setCredentialsNonExpired(true);
//            inventoryUserAccount.setEnabled(true);
//            inventoryUserAccount.setInventoryUser(inventoryUser);
//            inventoryUser.setInventoryUserAccount(inventoryUserAccount);
//            if(name.equals("ADMIN")){
//                adminRole.ifPresent(inventoryUserAccount::setInventoryRole);
//            }
//            if(name.equals("MANAGER")){
//                managerRole.ifPresent(inventoryUserAccount::setInventoryRole);
//            }
//            if(name.equals("VIEWER")){
//                viewerRole.ifPresent(inventoryUserAccount::setInventoryRole);
//            }
//             inventoryUserService.save(inventoryUser);
//        }
//
//    }


//
//    @Transactional
//    InventoryRole createRoleIfNotFound(String name) {
//        Optional<InventoryRole> roleOptional = inventoryRoleService.getRoleByName(name);
//        if (roleOptional.isEmpty()) {
//            InventoryRole inventoryRole = new InventoryRole();
//            inventoryRole.setRoleName(name);
//            return inventoryRoleService.save(inventoryRole);
//        }else{
//            return roleOptional.get();
//        }
//    }

}
