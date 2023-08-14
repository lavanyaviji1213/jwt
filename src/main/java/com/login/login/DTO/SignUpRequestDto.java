package com.login.login.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter

public class SignUpRequestDto {
    private Long userId;
    private String companyNo;
    private String roleNo;
    private String responsibilityNo;

    private String email;
    private String password;
    private Date createdAt = new Date();
    private Date updatedAt = new Date();
}
