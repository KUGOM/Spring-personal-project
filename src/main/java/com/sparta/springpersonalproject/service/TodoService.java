package com.sparta.springpersonalproject.service;

import com.sparta.springpersonalproject.dto.TodoRequestDto;
import com.sparta.springpersonalproject.dto.TodoResponseDto;
import com.sparta.springpersonalproject.entity.Admin;
import com.sparta.springpersonalproject.entity.Todo;
import com.sparta.springpersonalproject.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;
    private final AdminService adminService;

    public TodoResponseDto createTodo(TodoRequestDto todoRequestDto){
        Admin admin = adminService.getAdminEntity(todoRequestDto.getAdminId());
        Todo entity = new Todo(todoRequestDto);
        return TodoResponseDto.of(todoRepository.save(entity));
    }

    public TodoResponseDto getTodo(long todoId){
        Todo entity = todoRepository.findById(todoId).orElseThrow();
        return TodoResponseDto.of(entity);
    }

    public List<TodoResponseDto> getTodos(String date, String adminName) {
        List<Todo> entity = todoRepository.search(date, adminName);
        return entity.stream().map(TodoResponseDto::of).collect(Collectors.toList());
    }

    public TodoResponseDto updateTodo(long todoId, TodoRequestDto todoRequestDto){
        Todo entity = todoRepository.findById(todoId).orElseThrow();
        if(!entity.getPassword().equals(todoRequestDto.getPassword())){
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        if(todoRequestDto.getTodo() != null){
            entity.changeTodo(todoRequestDto.getTodo());
        }

        if(todoRequestDto.getAdminName() != null){
            entity.changeAdminName(todoRequestDto.getAdminName());
        }
        todoRepository.update(entity);

        Todo updateEntity = todoRepository.findById(todoId).orElseThrow();
        return TodoResponseDto.of(updateEntity);
    }

    public void deleteTodo(long todoId, TodoRequestDto todoRequestDto){
        Todo entity = todoRepository.findById(todoId).orElseThrow();
        if(!entity.getPassword().equals(todoRequestDto.getPassword())){
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }
        todoRepository.delete(todoId);
    }

//
//    public UserService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//    public User signup(SignupRequestDto requestDto) {
//        String nickname = requestDto.getNickname();
//        String username = requestDto.getUsername();
//        String password = requestDto.getPassword();
//
//        //중복 확인
//        Optional<User> checkUsername = userRepository.findByUsername(username);
//        if (checkUsername.isPresent()) {
//            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
//        }
//
//        //등록
//        User user = new User(requestDto);
//
//        return userRepository.save(user);
//    }
//    public void login(LoginRequestDto requestDto) {
//        String username = requestDto.getUsername();
//        String password = requestDto.getPassword();
//
//        //회원 확인
//        Optional<User> checkUser = userRepository.findByUsername(username);
//        if (checkUser.isPresent()) {
//            User user = checkUser.get();
//            if (!user.getPassword().equals(password)) {
//                throw new IllegalArgumentException("비밀번호가 틀렸습니다.");
//            }
//        } else {
//            throw new IllegalArgumentException("등록된 사용자가 없습니다.");
//        }
//    }
}
