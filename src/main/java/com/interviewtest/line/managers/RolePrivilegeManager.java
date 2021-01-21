package com.interviewtest.line.managers;


import com.interviewtest.line.entity.Privilege;
import com.interviewtest.line.entity.Role;
import com.interviewtest.line.entity.UserAccess;
import com.interviewtest.line.entity.UsersAccount;
import com.interviewtest.line.repository.PrivilegeRepository;
import com.interviewtest.line.repository.RoleRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
@Data
public class RolePrivilegeManager {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;
        
    
    public Authentication getUserAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public Object getPrincipal() {
        Authentication authentication = getUserAuthentication();
        //checking if there is an authenticate user before trying to access it
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return getUserAuthentication().getPrincipal();
        }
        return null;
    }

    public UserAccess getUserAccess() {
        Object principal = getPrincipal();
        if (principal != null) {
            return (UserAccess) principal;
        }
        return null;
    }

    public UsersAccount getUser() {
        UserAccess userAccess = getUserAccess();
        if (userAccess != null) {
            return userAccess.getUser();
        }
        return null;
    }

    public String getUsername() {
        Authentication authentication = getUserAuthentication();
        //checking if there is an authenticate user before trying to access it
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return getUserAccess().getUsername();
        }
        return null;
    }

    public List<String> getUserAuthorities() {
        List<String> authorities = new ArrayList<>();
        Authentication authentication = getUserAuthentication();
        //checking if there is an authenticate user before trying to access it
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            Object[] authoritiesObject = authentication.getAuthorities().toArray();
            for (Object authority : authoritiesObject) {
                authorities.add(authority.toString());
            }
            return authorities;
        }
        return null;
    }

    public boolean hasAuthority(String privilege) {
        Authentication authentication = getUserAuthentication();
        //checking if there is an authenticate user before trying to access it
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            Object[] authoritiesObject = authentication.getAuthorities().toArray();
            for (Object authority : authoritiesObject) {
                if (authority.toString().equals(privilege)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @param roles
     * @return
     */
    public boolean haveAccess(Collection<Role> roles) {
        List<String> privileges = getPrivileges(roles);
        return privileges.contains("ACCESS_PRIVILEGE");
    }
    
    /**
     * @param roles
     * @return
     */
    public Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> roles) {
        return getGrantedAuthorities(getPrivileges(roles));
    }

    /**
     * @param roles
     * @return
     */
    public List<String> getPrivileges(Collection<Role> roles) {
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

    /**
     * @param privileges
     * @return
     */
    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (privileges != null) {
            privileges.forEach((privilege) -> {
                authorities.add(new SimpleGrantedAuthority(privilege));
            });
        }
        return authorities;
    }

    /**
     * @param roles
     * @return
     */
    public List<String> getRoleNames(Collection<Role> roles) {
        if (roles == null || roles.isEmpty()) {
            return null;
        }
        List<String> userRoleNames = new ArrayList<>();
        roles.forEach((role) -> {
            userRoleNames.add(role.getName());
        });
        return userRoleNames;
    }

    @Transactional
    public Privilege persistPrivilege(String name) {
        name = name.toUpperCase();
        Privilege privilege = privilegeRepository.findByName(name);
        if (privilege == null) {
            privilege = new Privilege(name);
            getPrivilegeRepository().save(privilege);
        }
        return privilege;
    }

    @Transactional
    public Role persistRole(String name, Collection<Privilege> privileges) {
        name = name.toUpperCase();
        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
            role.setPrivileges(privileges);
            getRoleRepository().save(role);
        }
        return role;
    }
    
    @Transactional
    public Privilege createPrivilegeIfNotFound(String name) {
        return persistPrivilege(name);
    }
    
    @Transactional
    public Role createRoleIfNotFound(String name, Collection<Privilege> privileges) {
        return persistRole(name, privileges);
    }

    /**
     * @param name
     * @return
     */
    public Role getRole(String name) {
        return this.getRoleRepository().findByName(name);
    }

    /**
     * @param name
     * @return
     */
    public Privilege getPrivilege(String name) {
        return this.getPrivilegeRepository().findByName(name);
    }

}
