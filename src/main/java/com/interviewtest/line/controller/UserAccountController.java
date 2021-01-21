package com.interviewtest.line.controller;

import com.interviewtest.line.dto.LoginDto;
import com.interviewtest.line.dto.LoginRequestDto;
import com.interviewtest.line.dto.UserDto;
import com.interviewtest.line.entity.UsersAccount;
import com.interviewtest.line.managers.UserManagement;
import com.interviewtest.line.response.JsonResponse;
import com.interviewtest.line.security.JwtServiceProvider;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @author Oki-Peter
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class UserAccountController {

    @Autowired
    private JwtServiceProvider jwtServiceManager;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserManagement userManagement;
    
    @PostMapping(path = "/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequestDto loginRequestDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequestDto.getUsername(),
                            loginRequestDto.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            LoginDto loginDto = jwtServiceManager.generateJwtToken(authentication);
            if (loginDto == null) {
                return new ResponseEntity<>(new JsonResponse(HttpStatus.NOT_FOUND, "Invalid Username and/or Password!"), HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.ok(new JsonResponse("See Data Object for Details", loginDto));
        } catch (AuthenticationException ex) {
            Logger.getLogger(UserAccountController.class).log(Logger.Level.INFO, ex.getMessage() + ": Invalid Username and/or Password!");
            return new ResponseEntity<>(new JsonResponse(HttpStatus.NOT_FOUND, "Invalid Username and/or Password!"), HttpStatus.NOT_FOUND);
        }
    }

    
    @Transactional
    @PostMapping(path = "/create/user")
    public ResponseEntity<Object> CreateNewUser(@Valid @RequestBody UserDto userDto,
            HttpServletRequest request,
            HttpServletResponse response) {
        UsersAccount user = userManagement.getUserRepository().findByIdOrEmail(userDto.getId(), userDto.getEmail());
        if (user != null) {
            return new ResponseEntity<>(new JsonResponse(HttpStatus.BAD_REQUEST, "Invalid Request: User Account Already Exist!"), HttpStatus.BAD_REQUEST);
        }
        
        user = userManagement.saveUser(userDto);
        userDto.setId(user.getId());
        return ResponseEntity.ok(new JsonResponse("See Data Object for Details", userDto));
    }

    
    @Transactional
    @PostMapping(path = "/save/user")
    public ResponseEntity<Object> SaveUser(@RequestBody UserDto userDto,
            HttpServletRequest request,
            HttpServletResponse response) {
        UsersAccount user = userManagement.getUserRepository().findByIdOrEmail(userDto.getId(), userDto.getEmail());
        if (user == null) {
            return new ResponseEntity<>(new JsonResponse(HttpStatus.BAD_REQUEST, "Invalid Request: User Account Does Not Exist!"), HttpStatus.BAD_REQUEST);
        }
                
        user = userManagement.saveUser(userDto);
        userDto.setId(user.getId());
        return ResponseEntity.ok(new JsonResponse("See Data Object for Details", userDto));
    }
    
    
}
