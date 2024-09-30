package com.sparta.springpersonalproject.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class TodoRequestDto {
    private final String todo;
    private final Long adminId;
    private final String password;
}
