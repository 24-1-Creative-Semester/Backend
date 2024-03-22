package com.example.sejongproject.SejongProfile.DTO;

import com.example.sejongproject.SejongProfile.Entity.Profile;
import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ProfileDto {
    private String department;
    private String status;


    @Builder
    public ProfileDto(String department, String status){
        this.department = department;
        this.status = status;
    }

    public Profile toEntity(){
        return Profile.builder()
                .status(this.status)
                .department(this.department)
                .build();
    }
}
