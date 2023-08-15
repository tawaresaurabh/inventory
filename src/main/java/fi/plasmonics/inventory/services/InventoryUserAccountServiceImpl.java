package fi.plasmonics.inventory.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import fi.plasmonics.inventory.repo.InventoryUserAccountRepository;

@Service("userDetailsService")
public class InventoryUserAccountServiceImpl  {

    @Autowired
    private InventoryUserAccountRepository inventoryUserAccountRepository;


//    @Override
//    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
//        Optional<InventoryUserAccount> inventoryUserOptional = inventoryUserAccountRepository.findByUserName(s);
//        return inventoryUserOptional.map(InventoryUserDetails::new).orElseThrow(() -> new UsernameNotFoundException("Not found:" +  s));
//    }

}
