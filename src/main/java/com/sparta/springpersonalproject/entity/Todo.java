package com.sparta.springpersonalproject.entity;

import com.sparta.springpersonalproject.dto.TodoRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Todo {
    private Long id;
    private String todo;
    private Admin admin;
    private String password;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    public Todo(TodoRequestDto todoRequestDto, Admin admin){
        this.todo = todoRequestDto.getTodo();
        this.admin = admin;
        this.password = todoRequestDto.getPassword();
        this.createAt = LocalDateTime.now();
        this.updateAt = LocalDateTime.now();
    }

    public void changeId(long id){
        this.id = id;
    }

    public void changeTodo(String todo){
        this.todo = todo;
    }

    public void changeAdmin(Admin admin){
        this.admin = admin;
    }
}

