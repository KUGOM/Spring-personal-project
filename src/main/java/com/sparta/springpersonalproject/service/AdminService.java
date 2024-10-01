package com.sparta.springpersonalproject.service;

import com.sparta.springpersonalproject.dto.AdminRequestDto;
import com.sparta.springpersonalproject.dto.AdminResponseDto;
import com.sparta.springpersonalproject.entity.Admin;
import com.sparta.springpersonalproject.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;

    public AdminResponseDto createAdmin(AdminRequestDto adminRequestDto) {
        Admin entity = new Admin(adminRequestDto);
        return AdminResponseDto.of(adminRepository.save(entity));
    }

    public Admin getAdminEntity(long adminId) {return adminRepository.findById(adminId).orElseThrow();}

    public AdminResponseDto getAdmin(long adminId){
        Admin admin = adminRepository.findById(adminId).orElseThrow();
        return AdminResponseDto.of(admin);
    }

    public List<AdminResponseDto> getAdmins(){
        List<Admin> entity = adminRepository.search();
        return entity.stream().map(AdminResponseDto::of).collect(Collectors.toList());
    }

    public AdminResponseDto updateAdmin(long adminId, AdminRequestDto adminRequestDto) {
        Admin admin = adminRepository.findById(adminId).orElseThrow();
        if(adminRequestDto.getName() != null){
            admin.changeName(adminRequestDto.getName());
        }

        if(adminRequestDto.getEmail() != null){
            admin.changeEmail(adminRequestDto.getEmail());
        }

        adminRepository.update(admin);

        Admin updatedAdmin = adminRepository.findById(adminId).orElseThrow();
        return AdminResponseDto.of(updatedAdmin);
    }

    public void deleteAdmin(long adminId) {
        Admin admin = adminRepository.findById(adminId).orElseThrow();
        adminRepository.delete(admin.getId());
    }
}
