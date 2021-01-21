/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.interviewtest.line.repository;

import com.interviewtest.line.entity.WalletMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author G7
 */
public interface WalletMasterRepository extends JpaRepository<WalletMaster, Long> {

    @Query(value = "SELECT * FROM PAYMENT_MASTER WHERE ID =:walletMasterId ",
            nativeQuery = true)
    List<WalletMaster> findByPaymentMasterId(@Param("walletMasterId") Long walletMasterId);

    @Query(value = "SELECT * FROM PAYMENT_MASTER WHERE USER_PROFILE_ID =:userProfileId ",
            nativeQuery = true)
    WalletMaster findByUserProfileId(@Param("userProfileId") Long userProfileId);

    @Query(value = "SELECT * FROM PAYMENT_MASTER WHERE OUTLET_ID =:outletId ",
            nativeQuery = true)
    List<WalletMaster> findByOutletsId(@Param("outletId") Long outletId);


}
