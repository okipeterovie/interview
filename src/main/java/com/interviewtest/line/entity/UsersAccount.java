package com.interviewtest.line.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Oki-Peter
 */
@Data
@Entity
@Table(name = "USERS_ACCOUNT")
public class UsersAccount implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "EMAIL", nullable = false)
    private String email;

    @Column(name = "PASSWORD", nullable = false)
    private String password;
    
    @Column(name = "IS_ENABLED")
    private boolean enabled = true;

    @Column(name = "ACCOUNT_NON_EXPIRED")
    private boolean accountNonExpired = true;
    
    @Column(name = "CREDENTIAL_NON_EXPIRED")
    private boolean credentialsNonExpired = true;

    @Column(name = "ACCOUNT_NON_LOCKED")
    private boolean accountNonLocked = true;

    @CreationTimestamp
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy-MM-dd h:m:s")
    @Column(name = "TIME_CREATED")
    private LocalDateTime timeCreated;

    @UpdateTimestamp
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy-MM-dd h:m:s")
    @Column(name = "TIME_UPDATED")
    private LocalDateTime timeUpdated;

    @Column(name = "IS_DELETED", nullable = false)
    private boolean deleted = false;

    @ManyToMany
    @JoinTable(
            name = "USERS_ACCOUNT_ROLES",
            joinColumns = @JoinColumn(name = "users_account_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private List<Role> roles = new ArrayList<>();


    /**
     * @return
     */
    public boolean isAdmin() {
        return hasRole("ROLE_ADMIN");
    }

    /**
     * @param roleName
     * @return
     */
    public boolean hasRole(String roleName) {
        if (roles == null || roleName == null || "".equals(roleName) || roles.isEmpty()) {
            return false;
        }
        return roles.stream().anyMatch((role) -> (role.getName().equals(roleName)));
    }

    /**
     * @param privilege
     * @return
     */
    public boolean hasPrivilege(String privilege) {
        List<Privilege> collection = new ArrayList<>();
        if (roles != null) {
            roles.forEach((role) -> {
                collection.addAll(role.getPrivileges());
            });
            return collection.stream().anyMatch((userPrivilege) -> (userPrivilege.getName().equalsIgnoreCase(privilege)));
        }
        return false;
    }

    /**
     * @return
     */
    public List<String> getPrivileges() {
        List<String> privileges = new ArrayList<>();
        List<Privilege> collection = new ArrayList<>();
        if (roles != null) {
            roles.forEach((role) -> {
                collection.addAll(role.getPrivileges());
            });
            collection.forEach((item) -> {
                privileges.add(item.getName());
            });
        }
        return privileges;
    }

}
