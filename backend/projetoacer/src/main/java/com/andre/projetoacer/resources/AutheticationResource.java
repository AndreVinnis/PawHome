package com.andre.projetoacer.resources;

import com.andre.projetoacer.DTO.auth.AuthenticationDTO;
import com.andre.projetoacer.DTO.auth.LoginResponseDTO;
import com.andre.projetoacer.domain.GenericUser;
import com.andre.projetoacer.infra.Security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AutheticationResource {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;


    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthenticationDTO data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateTowen((GenericUser) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }
}
