package com.interviewtest.line.dto;

import com.interviewtest.line.validator.ConfirmPassword;
import lombok.Data;

/**
 * @author Ademola Aina
 */
@Data
@ConfirmPassword(confirmPasswordField = "confirmPassword", message = "Invalid Password and/or Confirm Password")
public class OutletsDto {

    private Long id;

    private Long usersAccountId;

    private String email;

    private String password;

    private String confirmPassword;

    private String outletName;

    private String phone;

    private String streetAddress;

    private String country = "NIGERIA";
}
