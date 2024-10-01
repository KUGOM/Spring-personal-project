package com.sparta.springpersonalproject.controller;

import com.sparta.springpersonalproject.dto.AdminRequestDto;
import com.sparta.springpersonalproject.dto.AdminResponseDto;
import com.sparta.springpersonalproject.service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @PostMapping("/admins")
    public ResponseEntity<AdminResponseDto> createAdmin(@Valid @RequestBody AdminRequestDto adminRequestDto) {
        AdminResponseDto adminResponseDto = adminService.createAdmin(adminRequestDto);
        return ResponseEntity.ok(adminResponseDto);
    }

    @GetMapping("/admins/{adminId}")
    public ResponseEntity<AdminResponseDto> getAdmin(@PathVariable long adminId) {
        AdminResponseDto adminResponseDto = adminService.getAdmin(adminId);
        return ResponseEntity.ok(adminResponseDto);
    }

    @GetMapping("/admins")
    public ResponseEntity<List<AdminResponseDto>> getAdmins() {
        List<AdminResponseDto> adminResponseDto = adminService.getAdmins();
        return ResponseEntity.ok(adminResponseDto);
    }

    @PutMapping("/admins/{adminid}")
    public ResponseEntity<AdminResponseDto> updateAdmin(
            @PathVariable long adminId,
            @RequestBody AdminRequestDto adminRequestDto
    ) {
        AdminResponseDto adminResponseDto = adminService.updateAdmin(adminId, adminRequestDto);
        return ResponseEntity.ok(adminResponseDto);
    }

    @DeleteMapping("/admins/{adminid}")
    public void deleteAdmin(@PathVariable long adminId) {
        adminService.deleteAdmin(adminId);
    }
}
