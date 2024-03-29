package fi.plasmonics.inventory.authentication;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


import fi.plasmonics.inventory.entity.InventoryRole;
import fi.plasmonics.inventory.entity.InventoryUserAccount;

public class InventoryUserDetails  {

    private InventoryUserAccount inventoryUserAccount;


    public InventoryUserDetails(InventoryUserAccount inventoryUserAccount) {
        this.inventoryUserAccount = inventoryUserAccount;
    }

//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//       return getAuthorities(Collections.singleton(inventoryUserAccount.getInventoryRole()));
//    }
//
//    @Override
//    public String getPassword() {
//        return inventoryUserAccount.getPassword();
//    }
//
//    @Override
//    public String getUsername() {
//        return inventoryUserAccount.getUserName();
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return inventoryUserAccount.isAccountNonExpired();
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return inventoryUserAccount.isAccountNonLocked();
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return inventoryUserAccount.isCredentialsNonExpired();
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return inventoryUserAccount.isEnabled();
//    }
//
//
//
//    private Collection<? extends GrantedAuthority> getAuthorities(
//            Collection<InventoryRole> roles) {
//        return getGrantedAuthorities(getPrivileges(roles));
//    }
//
//    private List<String> getPrivileges(Collection<InventoryRole> roles) {
//        List<String> privileges = new ArrayList<>();
//        for (InventoryRole role : roles) {
//            privileges.add(role.getRoleName());
//        }
//        return privileges;
//    }
//
//    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        for (String privilege : privileges) {
//            authorities.add(new SimpleGrantedAuthority(privilege));
//        }
//        return authorities;
//    }
}
