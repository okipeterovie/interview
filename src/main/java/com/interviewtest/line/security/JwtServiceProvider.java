package com.interviewtest.line.security;

import com.interviewtest.line.dto.LoginDto;
import com.interviewtest.line.entity.UserAccess;
import com.interviewtest.line.entity.UsersAccount;
import com.interviewtest.line.managers.UserManagement;
import io.jsonwebtoken.*;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author Ademola Aina
 */
@Data
@Service
public class JwtServiceProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtServiceProvider.class);

    @Value("${com.3line.interviewtest.jwtSecret}")
    private String jwtSecret;

    @Value("${com.3line.interviewtest.jwtExpiration}")
    private int jwtExpiration;

    @Autowired
    private UserManagement userManagement;
    
    private Claims claims;
    
    /**
     * @param authentication
     * @return 
     */
    public LoginDto generateJwtToken(Authentication authentication) {

        UserAccess userAccess = (UserAccess) authentication.getPrincipal();
        UsersAccount user = userAccess.getUser();
        List<String> roles = userManagement.getRolePrivilegeManager().getRoleNames(user.getRoles());
        List<String> privileges = userManagement.getRolePrivilegeManager().getPrivileges(user.getRoles());
        
        Claims claims = Jwts.claims().setSubject(user.getEmail());
        claims.put("userId", user.getId() + "");
        claims.put("role(s)", roles);
        claims.put("privileges(s)", user.getPrivileges());

        String jwt = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpiration * 1000))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
        
        LoginDto loginDto = new LoginDto();
        loginDto.setId(user.getId());
        loginDto.setEmail(user.getEmail());
        
        loginDto.setRoles(roles);
        loginDto.setPrivileges(privileges);
        loginDto.setToken(jwt);
        
        return loginDto;
    }

    /**
     * @param authToken
     * @return
     */
    public boolean validateJwtToken(String authToken) {
        try {
            setClaims(
                    Jwts.parser()
                        .setSigningKey(jwtSecret)
                        .parseClaimsJws(authToken)
                        .getBody()
            );
            String username = getClaims().getSubject();
            logger.info("claims" + claims.toString());
            return username != null;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature -> Message: {} ", e);
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token -> Message: {}", e);
        } catch (ExpiredJwtException e) {
            logger.error("Expired JWT token -> Message: {}", e);
        } catch (UnsupportedJwtException e) {
            logger.error("Unsupported JWT token -> Message: {}", e);
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty -> Message: {}", e);
        }
        return false;
    }
    

    /**
     * @param token
     * @return 
     */
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody().getSubject();
    }
    
    
}
