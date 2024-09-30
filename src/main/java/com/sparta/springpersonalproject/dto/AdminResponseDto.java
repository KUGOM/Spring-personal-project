package com.sparta.springpersonalproject.dto;

import com.sparta.springpersonalproject.entity.Admin;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
public class AdminResponseDto {
    private final long id;
    private final String name;
    private final String email;
    private final LocalDateTime createAt;
    private final LocalDateTime updateAt;

    public static AdminResponseDto of(Admin entity){
        return new AdminResponseDto(
                entity.getId(),
                entity.getName(),
                entity.getEmail(),
                entity.getCreateAt(),
                entity.getUpdateAt()
        );
    }
}
