package com.interviewtest.line.repository;

import com.interviewtest.line.entity.Outlets;
import com.interviewtest.line.entity.UserProfile;
import com.interviewtest.line.entity.UsersAccount;
import com.interviewtest.line.entity.WalletMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface OutletsRepository extends JpaRepository<Outlets, Long> {

    Outlets findByUsersAccount(UsersAccount usersAccount);

    Outlets findFirstByOutletsUsersIn(List<UserProfile> userProfiles);

    Outlets findByEmail(String email);

    List<Outlets> findAllByDeletedOrderById(boolean isDeleted);

    List<Outlets> findByDeleted(boolean isDeleted);

    @Query(value = "SELECT id FROM OUTLETS WHERE ID =:outletsId ",
            nativeQuery = true)
    Outlets findByOutletsId(@Param("outletsId") Long outletsId);






}
