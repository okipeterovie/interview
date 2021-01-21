package com.interviewtest.line.repository;


import com.interviewtest.line.entity.UsersAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface UserRepository extends JpaRepository<UsersAccount, Long> {

    UsersAccount findByIdOrEmail(Long id, String email);

    UsersAccount findByEmail(String email);

    Boolean existsByEmail(String email);

    UsersAccount findByEmailAndPassword(String email, String password);

    @Query(value = "SELECT u.* FROM users u "
            + " LEFT JOIN users_roles us ON u.id = us.user_id "
            + "WHERE us.user_id = ? ",
            nativeQuery = true)
    Collection<UsersAccount> findActiveUsersRolesById(Long user_id);

    Collection<UsersAccount> findByDeleted(boolean isDeleted);

}
