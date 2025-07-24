package com.joaopereira.renda_organziada.dtos;

import com.joaopereira.renda_organziada.entities.UserEntity;

public class LoginResponseDTO {
    private UserEntity user;
    private String token;

    public LoginResponseDTO(String token, UserEntity user) {
        this.token = token;
        this.user = user;
    }

    public UserEntity getUser() {
        return user;
    }
    public void setUser(UserEntity user) {
        this.user = user;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
}
