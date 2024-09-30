package com.sparta.springpersonalproject.entity;

import com.sparta.springpersonalproject.dto.AdminRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Admin {
    private Long id;
    private String name;
    private String email;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    public Admin(AdminRequestDto adminRequestDto) {
        this.name = adminRequestDto.getName();
        this.email = adminRequestDto.getEmail();
        this.createAt = LocalDateTime.now();
        this.updateAt = LocalDateTime.now();
    }

    public void changeId(long id) {this.id = id;}

    public void changeName(String name) {this.name = name;}

    public void changeEmail(String email) {this.email = email;}
}
