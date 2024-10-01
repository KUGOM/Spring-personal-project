package com.sparta.springpersonalproject.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class TodoRequestDto {
    @NotBlank(message = "Todo는 비워둘 수 없습니다.")
    @Size(max = 200, message = "Todo는 최대 200자 까지 작성 가능합니다.")
    private final String todo;
    private final Long adminId;
    @NotBlank(message = "비밀번호는 필수입력 값입니다")
    private final String password;
}
