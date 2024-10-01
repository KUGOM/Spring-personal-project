package com.sparta.springpersonalproject.service;

import com.sparta.springpersonalproject.dto.TodoRequestDto;
import com.sparta.springpersonalproject.dto.TodoResponseDto;
import com.sparta.springpersonalproject.entity.Admin;
import com.sparta.springpersonalproject.entity.Todo;
import com.sparta.springpersonalproject.exception.PasswordException;
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
        Todo entity = new Todo(todoRequestDto, admin);
        return TodoResponseDto.of(todoRepository.save(entity));
    }

    public TodoResponseDto getTodo(long todoId){
        Todo entity = todoRepository.findById(todoId).orElseThrow();
        return TodoResponseDto.of(entity);
    }

    public List<TodoResponseDto> getTodos(String date, String adminName, int page, int size) {
        List<Todo> entity = todoRepository.search(date, adminName, page, size);
        return entity.stream().map(TodoResponseDto::of).collect(Collectors.toList());
    }

    public TodoResponseDto updateTodo(long todoId, TodoRequestDto todoRequestDto){
        Todo entity = todoRepository.findById(todoId).orElseThrow();
        if(!entity.getPassword().equals(todoRequestDto.getPassword())){
            throw new PasswordException("비밀번호가 일치하지 않습니다.");
        }

        if(todoRequestDto.getTodo() != null){
            entity.changeTodo(todoRequestDto.getTodo());
        }

        Admin admin = adminService.getAdminEntity(todoRequestDto.getAdminId());
        if(admin != null){
            entity.changeAdmin(admin);
        }

        todoRepository.update(entity);

        Todo updateEntity = todoRepository.findById(todoId).orElseThrow();
        return TodoResponseDto.of(updateEntity);
    }

    public void deleteTodo(long todoId, TodoRequestDto todoRequestDto){
        Todo entity = todoRepository.findById(todoId).orElseThrow();
        if(!entity.getPassword().equals(todoRequestDto.getPassword())){
            throw new PasswordException("비밀번호가 일치하지 않습니다.");
        }
        todoRepository.delete(todoId);
    }
}
