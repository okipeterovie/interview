
package com.interviewtest.line.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.interviewtest.line.enumeration.UserType;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Oki-Peter
 */
@Data
@Entity
@Table(name="USER_PROFILE")
public class UserProfile implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "USER_ACCOUNT_ID", referencedColumnName = "id")
    private UsersAccount usersAccount;
    
    @Enumerated(EnumType.STRING)
    @Column(name="USER_TYPE", nullable = false)
    private UserType userType;
    
    @Column(name="FIRST_NAME", nullable = false)
    private String firstName;
    
    @Column(name="LAST_NAME")
    private String lastName;
    
    @Column(name="POSITION")
    private String position;
    
    @Column(name="EMAIL")
    private String email;
    
    @Column(name="PHONE")
    private String phone;
    
    @Column(name="STREET_ADDRESS")
    private String streetAddress;
    
    @Column(name="COUNTRY", nullable = false)
    private String country = "NIGERIA";
        
    @CreationTimestamp
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy-MM-dd h:m:s")
    @Column(name = "TIME_CREATED")
    private LocalDateTime timeCreated;
    
    @UpdateTimestamp
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy-MM-dd h:m:s")
    @Column(name = "TIME_UPDATED")
    private LocalDateTime timeUpdated;
    
    @Column(name="IS_DELETED", nullable = false)
    private boolean deleted = false;


}
