package fi.plasmonics.inventory.setup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import fi.plasmonics.inventory.entity.InventoryPrivilege;
import fi.plasmonics.inventory.entity.InventoryRole;
import fi.plasmonics.inventory.entity.InventoryUser;
import fi.plasmonics.inventory.entity.InventoryUserAccount;
import fi.plasmonics.inventory.services.InventoryPrivilegeService;
import fi.plasmonics.inventory.services.InventoryRoleService;
import fi.plasmonics.inventory.services.InventoryUserService;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;

    @Autowired
    private UserDetailsService inventoryUserDetailsService;

    @Autowired
    private InventoryUserService inventoryUserService;

    @Autowired
    private InventoryRoleService inventoryRoleService;

    @Autowired
    private InventoryPrivilegeService inventoryPrivilegeService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (alreadySetup)
            return;
        InventoryPrivilege readPrivilege
                = createPrivilegeIfNotFound("READ_PRIVILEGE");
        InventoryPrivilege writePrivilege
                = createPrivilegeIfNotFound("WRITE_PRIVILEGE");

        List<InventoryPrivilege> adminPrivileges = Arrays.asList(
                readPrivilege, writePrivilege);
        createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
        createRoleIfNotFound("ROLE_USER", Arrays.asList(readPrivilege));

        Optional<InventoryRole> adminRole = inventoryRoleService.getRoleByName("ROLE_ADMIN");

        InventoryUser inventoryUser = new InventoryUser();
        inventoryUser.setFirstName("TEST");
        inventoryUser.setLastName("TEST");
        inventoryUser.setEmail("TEST@TEST.COM");
        inventoryUser.setPhoneNumber("0000000000");
        inventoryUser.setCreatedBy("SOMEONE");
        inventoryUser.setCreateTime(Timestamp.from(Instant.now()));
        InventoryUserAccount inventoryUserAccount = new InventoryUserAccount();
        inventoryUserAccount.setUserName("TEST@TEST.COM");
        inventoryUserAccount.setPassword(passwordEncoder.encode("PASS"));
        inventoryUserAccount.setAccountNonExpired(true);
        inventoryUserAccount.setAccountNonLocked(true);
        inventoryUserAccount.setCredentialsNonExpired(true);
        inventoryUserAccount.setEnabled(true);
        inventoryUserAccount.setInventoryUser(inventoryUser);
        inventoryUser.setInventoryUserAccount(inventoryUserAccount);
        adminRole.ifPresent(inventoryRole -> inventoryUserAccount.setInventoryRoles(Collections.singleton(inventoryRole)));
        inventoryUserService.save(inventoryUser);
        alreadySetup = true;
    }


    @Transactional
    InventoryPrivilege createPrivilegeIfNotFound(String name) {
        Optional<InventoryPrivilege> privilegeOptional = inventoryPrivilegeService.getPrivilegeByName(name);
        if (!privilegeOptional.isPresent()) {
            InventoryPrivilege privilege = new InventoryPrivilege();
            privilege.setPrivilegeName(name);
            return inventoryPrivilegeService.save(privilege);
        }else{
            return privilegeOptional.get();
        }
    }

    @Transactional
    InventoryRole createRoleIfNotFound(
            String name, Collection<InventoryPrivilege> privileges) {

        Optional<InventoryRole> roleOptional = inventoryRoleService.getRoleByName(name);

        if (!roleOptional.isPresent()) {
            InventoryRole inventoryRole = new InventoryRole();
            inventoryRole.setRoleName(name);
            inventoryRole.setInventoryPrivileges(privileges);
            return inventoryRoleService.save(inventoryRole);
        }else{
            return roleOptional.get();
        }
    }

}
