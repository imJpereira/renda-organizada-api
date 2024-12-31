package com.joaopereira.renda_organziada.controllers;

import org.springframework.beans.BeanUtils;
import com.joaopereira.renda_organziada.dtos.SignUpDTO;
import com.joaopereira.renda_organziada.entities.UserEntity;
import com.joaopereira.renda_organziada.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    final private UserService userService;

    AuthController (UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserEntity> create(@RequestBody SignUpDTO signup) throws Exception {

        UserEntity newUser = new UserEntity();

        BeanUtils.copyProperties(signup, newUser);

        userService.save(newUser);

        return ResponseEntity.ok(newUser);
    }

}
