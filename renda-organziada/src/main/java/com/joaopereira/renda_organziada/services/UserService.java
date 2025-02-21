package com.joaopereira.renda_organziada.services;

import com.joaopereira.renda_organziada.entities.UserEntity;
import com.joaopereira.renda_organziada.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.joaopereira.renda_organziada.configs.security.WebSecurityConfig;

import java.util.List;

@Service
public class UserService {
    final private UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserEntity save(UserEntity newUser) throws Exception {

        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));

        repository.save(newUser);

        return newUser;
    }

    public List<UserEntity> findAll() throws Exception {
        return repository.findAll();
    }

}
