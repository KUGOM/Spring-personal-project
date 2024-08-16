package com.sparta.springpersonalproject.entity;

import com.sparta.springpersonalproject.dto.SignupRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class User {
    private Long id;
    private String nickname;
    private String username;
    private String password;

    public User(SignupRequestDto requestDto) {
        this.nickname = requestDto.getNickname();
        this.username = requestDto.getUsername();
        this.password = requestDto.getPassword();
    }

    public void updateUser(SignupRequestDto requestDto) {
        this.nickname = requestDto.getNickname();
        this.username = requestDto.getUsername();
    }
}
