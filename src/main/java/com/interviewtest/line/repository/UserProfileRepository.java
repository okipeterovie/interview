package com.interviewtest.line.repository;

import com.interviewtest.line.entity.UserProfile;
import com.interviewtest.line.entity.UsersAccount;
import com.interviewtest.line.enumeration.UserType;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

    UserProfile findByUsersAccount(UsersAccount usersAccount);
    
    UserProfile findByUserType(UserType userType);
    
    UserProfile findByEmail(String email);

    @Query(value="SELECT * FROM USER_PROFILE WHERE EMAIL=:userProfileEmail",
            nativeQuery = true)
    List <UserProfile> findUserProfileByEmail(@Param("userProfileEmail") String userProfileEmail);

    List<UserProfile> findAllByDeletedOrderById(boolean isDeleted);
    
    List<UserProfile> findByDeleted(boolean isDeleted);
        
}

