package com.interviewtest.line.repository;

import com.interviewtest.line.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);

    @Query(value = "SELECT r.* FROM role r "
            + " LEFT JOIN USERS_ROLES us ON r.id = us.role_id "
            + "WHERE us.role_id = ? ",
            nativeQuery = true)
    Collection<Role> findActiveUsersRolesById(Long role_id);

     Collection<Role> findByDeleted(int isDeleted);
     
}
