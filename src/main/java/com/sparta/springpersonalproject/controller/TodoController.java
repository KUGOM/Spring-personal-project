package com.sparta.springpersonalproject.controller;

import com.sparta.springpersonalproject.dto.TodoRequestDto;
import com.sparta.springpersonalproject.dto.TodoResponseDto;
import com.sparta.springpersonalproject.service.TodoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TodoController {
    private final TodoService todoService;

    @PostMapping("/todos")
    public ResponseEntity<TodoResponseDto> createTodo(@Valid @RequestBody TodoRequestDto todoRequestDto) {
        TodoResponseDto todoResponseDto = todoService.createTodo(todoRequestDto);
        return ResponseEntity.ok(todoResponseDto);
    }

    @GetMapping("/todos/{todoId}")
    public ResponseEntity<TodoResponseDto> getTodo(@PathVariable long todoId) {
        TodoResponseDto todoResponseDto = todoService.getTodo(todoId);
        return ResponseEntity.ok(todoResponseDto);
    }

    @GetMapping("/todos")//다건조화
    public ResponseEntity<List<TodoResponseDto>> getTodos(
            @RequestParam(required = false) String date,
            @RequestParam(required = false) String adminName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        List<TodoResponseDto> todoResponseDto = todoService.getTodos(date, adminName, page, size);
        return ResponseEntity.ok(todoResponseDto);
    }

    @PutMapping("/todos/{todoId}")//update가 쿼리문과 일치하기 때문에 삭제
    public ResponseEntity<TodoResponseDto> updateTodo(
            @PathVariable long todoId,
            @RequestBody TodoRequestDto todoRequestDto
    ){
        TodoResponseDto todoResponseDto = todoService.updateTodo(todoId, todoRequestDto);
        return ResponseEntity.ok(todoResponseDto);
    }

    @DeleteMapping("/todos/{todoId}")
    public void deleteTodo(
            @PathVariable long todoId,
            @RequestBody TodoRequestDto todoRequestDto
    ) {
        todoService.deleteTodo(todoId, todoRequestDto);
    }
}
