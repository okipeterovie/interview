
package com.interviewtest.line.dto;

import com.interviewtest.line.enumeration.UserType;
import com.interviewtest.line.validator.ConfirmPassword;
import com.interviewtest.line.validator.Email;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;

/**
 * @author Ademola Aina
 */
@Data
@ConfirmPassword(confirmPasswordField="confirmPassword", message = "Invalid Password and/or Confirm Password")
public class UserDto implements Serializable {
    
    private Long id;
    
    @Email
    private String email;
    
    private String password;
    
    private String confirmPassword;
        
    @Enumerated(value=EnumType.STRING)
    private UserType userType;
    
}
