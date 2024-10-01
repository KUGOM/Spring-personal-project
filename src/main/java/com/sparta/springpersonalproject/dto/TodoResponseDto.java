package com.sparta.springpersonalproject.dto;

import com.sparta.springpersonalproject.entity.Todo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
public class TodoResponseDto {
    private final Long id;
    private final String todo;
    private final Long adminId;
    private final String adminName;
    private final String adminEmail;
    private final LocalDateTime createAt;
    private final LocalDateTime updateAt;

    public static TodoResponseDto of(Todo entity){
        return new TodoResponseDto(
                entity.getId(),
                entity.getTodo(),
                entity.getAdmin().getId(),
                entity.getAdmin().getName(),
                entity.getAdmin().getEmail(),
                entity.getCreateAt(),
                entity.getUpdateAt()
        );
    }
}
