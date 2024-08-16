package com.sparta.springpersonalproject.dto;

import com.sparta.springpersonalproject.entity.User;
import lombok.Getter;

@Getter
public class SignupResponseDto {
    private Long id;
    private String nickname;
    private String username;
    private String password;

    public SignupResponseDto(User user) {
        this.id = user.getId();
        this.nickname = user.getNickname();
        this.username = user.getUsername();
        this.password = user.getPassword();
    }
}