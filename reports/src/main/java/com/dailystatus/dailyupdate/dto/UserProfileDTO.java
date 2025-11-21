package com.dailystatus.dailyupdate.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserProfileDTO {

    private String email;
    private String name;
    private String phone;
    private LocalDate dateOfBirth;
    private String gender;
    private String profilePic;

}
