package com.sparta.springpersonalproject.entity;

import com.sparta.springpersonalproject.dto.AdminRequestDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
