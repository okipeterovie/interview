
package com.interviewtest.line.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;

/**
 * @author Oki-Peter
 */
@Data
@Entity
@Table(name="PRIVILEGE")
public class Privilege implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    
    @Column(name="NAME", nullable = false)
    private String name;
    
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

    @ManyToMany(mappedBy = "privileges")
    private Collection<Role> roles;
    
    public Privilege() {
    }

    public Privilege(String name) {
        this.name = name;
    }
    
}
