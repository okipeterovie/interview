
package com.interviewtest.line.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name="OUTLETS")
public class Outlets implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    
    @OneToOne
    @JoinColumn(name = "USER_ACCOUNT_ID", referencedColumnName = "id")
    private UsersAccount usersAccount;
    
    @Column(name="OUTLET_NAME", nullable = false)
    private String outletName;
    
    @Column(name="EMAIL")
    private String email;
    
    @Column(name="PHONE")
    private String phone;
    
    @Column(name="STREET_ADDRESS")
    private String streetAddress;
    
    @Column(name="COUNTRY", nullable = false)
    private String country = "NIGERIA";

    
    @OneToMany
    @JoinTable(
            name = "OUTLETS_USER",
            joinColumns = @JoinColumn(name = "outlets_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_profile_id", referencedColumnName = "id")
    )
    private List<UserProfile> outletsUsers;
        
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
