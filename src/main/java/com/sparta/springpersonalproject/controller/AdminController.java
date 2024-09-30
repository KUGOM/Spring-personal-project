package com.sparta.springpersonalproject.controller;

import com.sparta.springpersonalproject.dto.AdminRequestDto;
import com.sparta.springpersonalproject.dto.AdminResponseDto;
import com.sparta.springpersonalproject.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @PostMapping("/admin")
    public ResponseEntity<AdminResponseDto> createAdmin(@RequestBody AdminRequestDto adminRequestDto) {
        AdminResponseDto adminResponseDto = AdminService.createAdmin(adminRequestDto);
        return ResponseEntity.ok(adminResponseDto);
    }

    @GetMapping("/admin/{adminId}")
    public ResponseEntity<AdminResponseDto> getAdmin(@PathVariable long adminId) {
        AdminResponseDto adminResponseDto = adminService.getAdmin(adminId);
        return ResponseEntity.ok(adminResponseDto);
    }

    @GetMapping("/admins")
    public ResponseEntity<List<AdminResponseDto>> getAdmins() {
        List<AdminResponseDto> adminResponseDto = adminService.getAdmins();
        return ResponseEntity.ok(adminResponseDto);
    }

    @PutMapping("/admin/update/{adminid}")
    public ResponseEntity<AdminResponseDto> updateAdmin(
            @PathVariable long adminId,
            @RequestBody AdminRequestDto adminRequestDto
    ) {
        AdminResponseDto adminResponseDto = adminService.updateAdmin(adminId, adminRequestDto);
        return ResponseEntity.ok(adminResponseDto);
    }

    @DeleteMapping("/admin/delete/{adminid}")
    public void deleteAdmin(@PathVariable long adminId) {
        adminService.deleteAdmin(adminId);
    }
}
