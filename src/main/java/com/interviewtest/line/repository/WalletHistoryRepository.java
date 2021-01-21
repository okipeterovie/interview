/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.interviewtest.line.repository;

import com.interviewtest.line.entity.WalletHistory;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 *
 * @author Oki-Peter
 */
public interface WalletHistoryRepository extends JpaRepository<WalletHistory, Long> {

    @Query(value="SELECT * FROM PAYMENT_HISTORY WHERE rrr =:RRR LIMIT 1 ",
            nativeQuery = true)
    WalletHistory findFirstByTellerOrderByIdDesc(@Param("RRR") String RRR);

    @Query(value="SELECT * FROM PAYMENT_HISTORY WHERE rrr =:RRR ",
            nativeQuery = true)
   List <WalletHistory> findByRRR(@Param("RRR") String RRR);

    @Query(value="SELECT * FROM PAYMENT_HISTORY WHERE WALLET_MASTER_ID =:walletMasterId ",
            nativeQuery = true)
    List <WalletHistory> findByWalletMasterId(@Param("walletMasterId") Long walletMasterId);

}



