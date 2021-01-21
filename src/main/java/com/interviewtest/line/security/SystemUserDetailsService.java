package com.interviewtest.line.security;

import com.interviewtest.line.entity.UserAccess;
import com.interviewtest.line.entity.UsersAccount;
import com.interviewtest.line.managers.UserManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SystemUserDetailsService implements UserDetailsService {

    @Autowired
    private UserManagement userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UsersAccount user = userService.getUserRepository().findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }

        //Extensive check to determine if user account should be enabled. A user must have a minimum authority of ACCESS_PRIVILEGE.
        boolean haveAccess = userService.getRolePrivilegeManager().haveAccess(user.getRoles());
        user.setEnabled(user.isEnabled() && haveAccess);        
        return new UserAccess(user, userService.getRolePrivilegeManager().getAuthorities(user.getRoles()));
    }
    
}
