package com.sparta.springpersonalproject.controller;

import com.sparta.springpersonalproject.dto.TodoRequestDto;
import com.sparta.springpersonalproject.dto.TodoResponseDto;
import com.sparta.springpersonalproject.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TodoController {
    private final TodoService todoService;

    @PostMapping("/todo")
    public ResponseEntity<TodoResponseDto> createTodo(@RequestBody TodoRequestDto todoRequestDto) {
        TodoResponseDto todoResponseDto = todoService.createTodo(todoRequestDto);
        return ResponseEntity.ok(todoResponseDto);
    }

    @GetMapping("/todo/{todoId}")
    public ResponseEntity<TodoResponseDto> getTodo(@PathVariable long todoId) {
        TodoResponseDto todoResponseDto = todoService.getTodo(todoId);
        return ResponseEntity.ok(todoResponseDto);
    }

    @GetMapping("/todos")
    public ResponseEntity<List<TodoResponseDto>> getTodos(
            @RequestParam(required = false) String date,
            @RequestParam(required = false) String adminName
    ) {
        List<TodoResponseDto> todoResponseDto = todoService.getTodos(date, adminName);
        return ResponseEntity.ok(todoResponseDto);
    }

    @PutMapping("/todo/update/{todoId}")
    public ResponseEntity<TodoResponseDto> updateTodo(
            @PathVariable long todoId,
            @RequestBody TodoRequestDto todoRequestDto
    ){
        TodoResponseDto todoResponseDto = todoService.updateTodo(todoId, todoRequestDto);
        return ResponseEntity.ok(todoResponseDto);
    }

    @DeleteMapping("/todo/delete/{todoId}")
    public void deleteTodo(
            @PathVariable long todoId,
            @RequestBody TodoRequestDto todoRequestDto
    ) {
        todoService.deleteTodo(todoId, todoRequestDto);
    }

    /*private final Map<Long, User> userList = new HashMap<>();

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
    }*/
}
