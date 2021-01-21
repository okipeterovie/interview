
package com.interviewtest.line.dto;

import lombok.Data;

import java.util.List;

/**
 * @author Oki-Peter
 */
@Data
public class LoginDto {
    
    private Long id;
    
    private String email;
        
    private List<String> roles;

    private List<String> privileges;
    
    private String token;
    
}
