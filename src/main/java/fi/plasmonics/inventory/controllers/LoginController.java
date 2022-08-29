package fi.plasmonics.inventory.controllers;

import fi.plasmonics.inventory.authentication.InventoryUserDetails;
import fi.plasmonics.inventory.authentication.JWTUtils;
import fi.plasmonics.inventory.model.request.login.LoginUser;
import fi.plasmonics.inventory.model.response.auth.JWTResponse;
import fi.plasmonics.inventory.repo.InventoryRoleRepository;
import fi.plasmonics.inventory.repo.InventoryUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/api/auth")
public class LoginController {

    @Autowired AuthenticationManager authenticationManager;
    @Autowired InventoryUserRepository userRepository;
    @Autowired InventoryRoleRepository roleRepository;
    @Autowired PasswordEncoder encoder;
    @Autowired JWTUtils jwtUtils;


    @PostMapping("/signin")
    public ResponseEntity<JWTResponse> authenticateUser( @RequestBody LoginUser loginUser) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginUser.getUserName(), loginUser.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        InventoryUserDetails userDetails = (InventoryUserDetails) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
            .map(item -> item.getAuthority())
            .collect(Collectors.toList());
        return ResponseEntity.ok(new JWTResponse(jwt,
            userDetails.getUsername(),
            roles));
    }

}
