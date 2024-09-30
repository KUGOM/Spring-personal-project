package com.sparta.springpersonalproject.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class AdminRequestDto {
    private final String name;
    private final String email;
}
