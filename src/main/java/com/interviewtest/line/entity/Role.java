
package com.interviewtest.line.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name="ROLE")
public class Role implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    
    @Column(name = "NAME", nullable = false)
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

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "ROLES_PRIVILEGES",
            joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "privilege_id", referencedColumnName = "id")
    )
    private Collection<Privilege> privileges;
    
    
    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }
    
    
    /**
     * @param privilege
     * @return
     */
    public boolean hasPrivilege(String privilege) {
        if (privileges != null) {
            return privileges.stream().anyMatch((userPrivilege) -> (userPrivilege.getName().equals(privilege)));
        }
        return false;
    }
    
}
