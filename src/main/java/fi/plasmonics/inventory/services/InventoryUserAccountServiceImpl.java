package fi.plasmonics.inventory.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

import fi.plasmonics.inventory.authentication.InventoryUserDetails;
import fi.plasmonics.inventory.entity.InventoryUserAccount;
import fi.plasmonics.inventory.repo.InventoryUserAccountRepository;

@Service("userDetailsService")
public class InventoryUserAccountServiceImpl implements UserDetailsService {

    @Autowired
    private InventoryUserAccountRepository inventoryUserAccountRepository;


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<InventoryUserAccount> inventoryUserOptional = inventoryUserAccountRepository.findByUserName(s);
        return inventoryUserOptional.map(InventoryUserDetails::new).orElseThrow(() -> new UsernameNotFoundException("Not found:" +  s));
    }
}
