package com.andre.projetoacer.services;

import com.andre.projetoacer.repository.InstitutionRepository;
import com.andre.projetoacer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InstitutionRepository institutionRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user = userRepository.findByEmail(username);
        if (user != null) return user;

        UserDetails institution = institutionRepository.findByEmail(username);
        if (institution != null) return institution;

        throw new UsernameNotFoundException("Usuário não encontrado");
    }
}
