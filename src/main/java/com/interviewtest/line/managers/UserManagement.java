package com.interviewtest.line.managers;

import com.interviewtest.line.dto.UserDto;
import com.interviewtest.line.entity.Role;
import com.interviewtest.line.entity.UsersAccount;
import com.interviewtest.line.repository.UserProfileRepository;
import com.interviewtest.line.repository.UserRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@Data
public class UserManagement {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private RolePrivilegeManager rolePrivilegeManager;

    
    @Transactional
    public UsersAccount persistAdminUser(UsersAccount user) {
        return persistUser(user, Arrays.asList(getRolePrivilegeManager().getRole("ROLE_ADMIN")));
    }

    @Transactional
    public UsersAccount persistSupervisorUser(UsersAccount user) {
        List<Role> roles = Arrays.asList(getRolePrivilegeManager().getRole("ROLE_USER_SUPERVISOR"));
        return persistUser(user, roles);
    }
    
    @Transactional
    public UsersAccount persistBankUser(UsersAccount user) {
        List<Role> roles = Arrays.asList(getRolePrivilegeManager().getRole("ROLE_BANK_USER"));
        return persistUser(user, roles);
    }
    
    @Transactional
    public UsersAccount persistAuditorUser(UsersAccount user) {
        List<Role> roles = Arrays.asList(getRolePrivilegeManager().getRole("ROLE_AUDITORS_USER"));
        return persistUser(user, roles);
    }

    @Transactional
    public UsersAccount persistUser(UsersAccount user, List<Role> roles) {
        UsersAccount userRecord = findByUsername(user.getEmail());
        if (userRecord instanceof UsersAccount) {
            user.setId(userRecord.getId());
        } else {
            user.setTimeCreated(LocalDateTime.now());
        }

        if (roles == null) {
            roles = new ArrayList<>();
        }

        if (user.getRoles() == null) {
            user.setRoles(roles);
        } else {
            user.getRoles().addAll(roles);
        }

        if (user.getRoles().isEmpty()) {
            user.getRoles().add(getRolePrivilegeManager().getRole("ROLE_USER"));
        }

        user.setEnabled(getRolePrivilegeManager().haveAccess(user.getRoles()));
        user.setAccountNonExpired(true);
        user.setCredentialsNonExpired(true);
        user.setAccountNonLocked(true);

        return getUserRepository().save(user);
    }

    @Transactional
    public UsersAccount persistUser(UsersAccount user) {
        return this.persistUser(user, null);
    }

    /**
     * @param user
     * @param userDto
     * @return
     */

    public UsersAccount saveUser(UsersAccount user, UserDto userDto) {
        user.setEmail(userDto.getEmail());
        user.setPassword(getBCryptPasswordEncoder().encode(userDto.getPassword()));
        List<Role> roles = user.getRoles();

        if (userDto.getUserType() != null){
            roles.add(getRolePrivilegeManager().getRole(userDto.getUserType().getRoleName()));
        }

        user.setRoles(roles);
        user = persistUser(user);
        userDto.setId(user.getId());
        return user;
    }

    public UsersAccount editUser(UsersAccount user, UserDto userDto) {
        user.setEmail(userDto.getEmail());
        user.setPassword(getBCryptPasswordEncoder().encode(userDto.getPassword()));
        List<Role> roles = user.getRoles();

        //added here for the update to prevent duplication of roles
        if (roles == null){
            if (userDto.getUserType() != null){
                roles.add(getRolePrivilegeManager().getRole(userDto.getUserType().getRoleName()));
            }
        }
        user.setRoles(roles);
        user = persistUser(user);
        userDto.setId(user.getId());
        return user;
    }

    /**
     * @param userDto
     * @return
     */
    public UsersAccount saveUser(UserDto userDto) {
        UsersAccount user = getUserRepository().findByIdOrEmail(userDto.getId(), userDto.getEmail());
        if (user == null) {
            user = new UsersAccount();
        }
        return saveUser(user, userDto);
    }

    public UsersAccount editUser(UserDto userDto) {
        UsersAccount user = getUserRepository().findByIdOrEmail(userDto.getId(), userDto.getEmail());
        if (user == null) {
            user = new UsersAccount();
        }
        return editUser(user, userDto);
    }

    /**
     * @param email
     * @return
     */
    public UsersAccount findByUsername(String email) {
        return getUserRepository().findByEmail(email);
    }

    /**
     * @param id
     * @param email
     * @return
     */
    public UsersAccount findByIdOrUsername(Long id, String email) {
        return getUserRepository().findByIdOrEmail(id, email);
    }

}
