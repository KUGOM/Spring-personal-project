package com.sparta.springpersonalproject.service;

import com.sparta.springpersonalproject.dto.LoginRequestDto;
import com.sparta.springpersonalproject.dto.SignupRequestDto;
import com.sparta.springpersonalproject.entity.User;
import com.sparta.springpersonalproject.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public User signup(SignupRequestDto requestDto) {
        String nickname = requestDto.getNickname();
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();

        //중복 확인
        Optional<User> checkUsername = userRepository.findByUsername(username);
        if (checkUsername.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        //등록
        User user = new User(requestDto);

        return userRepository.save(user);
    }
    public void login(LoginRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();

        //회원 확인
        Optional<User> checkUser = userRepository.findByUsername(username);
        if (checkUser.isPresent()) {
            User user = checkUser.get();
            if (!user.getPassword().equals(password)) {
                throw new IllegalArgumentException("비밀번호가 틀렸습니다.");
            }
        } else {
            throw new IllegalArgumentException("등록된 사용자가 없습니다.");
        }
    }
}
