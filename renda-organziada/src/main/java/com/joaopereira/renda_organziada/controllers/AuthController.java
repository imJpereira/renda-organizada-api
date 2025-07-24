package com.joaopereira.renda_organziada.controllers;

import com.joaopereira.renda_organziada.configs.security.TokenService;
import com.joaopereira.renda_organziada.dtos.LoginResponseDTO;
import com.joaopereira.renda_organziada.entities.ExpenseEntity;
import org.apache.catalina.User;
import org.apache.coyote.Response;
import org.springframework.beans.BeanUtils;
import com.joaopereira.renda_organziada.dtos.AuthDTO;
import com.joaopereira.renda_organziada.entities.UserEntity;
import com.joaopereira.renda_organziada.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    final private UserService userService;
    final private AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public AuthController(UserService userService, AuthenticationManager authenticationManager, TokenService tokenService) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping("/signin")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody AuthDTO user) throws Exception {

        var usernamePassword = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        UserEntity userEntity = (UserEntity) auth.getPrincipal();

        var token = tokenService.generateToken(userEntity);

        return ResponseEntity.status(HttpStatus.OK).body(new LoginResponseDTO(token, userEntity));
    }

    @PostMapping("/signup")
    public ResponseEntity<UserEntity> create(@RequestBody AuthDTO user) throws Exception {
        if (userService.findByUsername(user.getUsername()) != null) throw new IllegalArgumentException("Username is already used");

        var encryptedPassword = new BCryptPasswordEncoder().encode(user.getPassword());
        UserEntity newUser = new UserEntity(user.getUsername(), user.getEmail(), user.getPassword());
        userService.save(newUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @ExceptionHandler
    public ResponseEntity<String> handlerMethod(Exception e) {
        String msg = e.getMessage().replaceAll("\r\n", "");
        return ResponseEntity.badRequest().body(msg);
    }
}
