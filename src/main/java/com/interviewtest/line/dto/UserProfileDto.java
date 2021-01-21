
package com.interviewtest.line.dto;

import com.interviewtest.line.enumeration.UserType;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

/**
 * @author Oki-Peter
 */
@Data
public class UserProfileDto {
    
    private Long id;
    
    private Long usersAccountId;
    
    @Enumerated(EnumType.STRING)
    private UserType userType;
    
    private String firstName;
    
    private String lastName;
    
    private String position;
    
    private String email;
    
    private String phone;
    
    private String streetAddress;
    
    private String country = "NIGERIA";
    
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy-MM-dd h:m:s")
    private LocalDateTime timeCreated;
    
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy-MM-dd h:m:s")
    private LocalDateTime timeUpdated;
    
    private boolean deleted = false;
    
    private String password;
    
    private String confirmPassword;



}
