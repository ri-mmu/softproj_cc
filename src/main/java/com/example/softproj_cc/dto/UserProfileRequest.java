package com.example.softproj_cc.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProfileRequest {
    private String gender;
    private Boolean isActive;
}