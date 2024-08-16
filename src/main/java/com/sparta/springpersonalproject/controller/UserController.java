package com.sparta.springpersonalproject.controller;

import com.sparta.springpersonalproject.dto.LoginRequestDto;
import com.sparta.springpersonalproject.dto.SignupRequestDto;
import com.sparta.springpersonalproject.dto.SignupResponseDto;
import com.sparta.springpersonalproject.entity.User;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
public class UserController {

    private final Map<Long, User> userList = new HashMap<>();

    @PostMapping("/signup")
    public SignupResponseDto signup(@RequestBody SignupRequestDto requestDto) {
        // RequestDto -> Entity
        User user = new User(requestDto);

        Long maxId = userList.size() > 0 ? Collections.max(userList.keySet()) + 1 : 1;
        user.setId(maxId);

        //DB 저장
        userList.put(user.getId(), user);

        //Entity -> ResponseDto
        SignupResponseDto signupResponseDto = new SignupResponseDto(user);

        return signupResponseDto;
    }

    @GetMapping("/login")
    public List<SignupResponseDto> login(@RequestBody LoginRequestDto requestDto) {
        List<SignupResponseDto> responseList = userList.values().stream().map(SignupResponseDto::new).toList();

        return responseList;
    }

    @PutMapping("/update{id}")
    public Long updateUser(@PathVariable Long id, @RequestBody SignupRequestDto requestDto) {
        if (userList.containsKey(id)) {
            User user = userList.get(id);

            user.updateUser(requestDto);
            return user.getId();
        } else {
            throw new IllegalArgumentException("해당 유저가 존재하지 않습니다.");
        }
    }

    @DeleteMapping("/delete{id}")
    public Long deleteUser(@PathVariable Long id) {
        if (userList.containsKey(id)) {
            userList.remove(id);
            return id;
        } else {
            throw new IllegalArgumentException("해당 유저가 존재하지 않습니다.");
        }
    }
}
