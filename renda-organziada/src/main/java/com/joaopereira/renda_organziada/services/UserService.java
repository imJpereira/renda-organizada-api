package com.joaopereira.renda_organziada.services;

import com.joaopereira.renda_organziada.entities.UserEntity;
import com.joaopereira.renda_organziada.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    final private UserRepository repository;

    UserService(UserRepository repository) {
        this.repository = repository;
    }

    public UserEntity save(UserEntity newUser) throws Exception {

        repository.save(newUser);

        return newUser;
    }

    public List<UserEntity> findAll() throws Exception {
        return repository.findAll();
    }

}
