/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.interviewtest.line.services;

import com.interviewtest.line.dto.WalletMasterDto;
import com.interviewtest.line.entity.Outlets;
import com.interviewtest.line.entity.UserProfile;
import com.interviewtest.line.entity.WalletMaster;
import com.interviewtest.line.repository.OutletsRepository;
import com.interviewtest.line.repository.UserProfileRepository;
import com.interviewtest.line.repository.WalletHistoryRepository;
import com.interviewtest.line.repository.WalletMasterRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * @author G7
 */

@Data
@Service
public class WalletMasterService {

    @Autowired
    private OutletsRepository outletsRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private WalletHistoryRepository walletHistoryRepository;

    @Autowired
    private WalletMasterRepository walletMasterRepository;
    

    private Logger logger = Logger.getLogger(WalletMasterService.class.getName());

    public List<WalletMasterDto> findPaymentDetails(List<WalletMaster> walletMasters) {
        List<WalletMasterDto> walletMasterDtos = new ArrayList<>();
        if (walletMasters != null && !walletMasters.isEmpty()) {
            walletMasters.stream().map((walletMaster) -> {
                
                Long paymentId = walletMaster.getId();
               
                LocalDateTime timeCreated = walletMaster.getTimeCreated();
                LocalDateTime lastUpdated = walletMaster.getLastUpdated();
                Double amountAvailable = walletMaster.getTotalAmountAvailable();
                

                WalletMasterDto walletMasterDto = new WalletMasterDto();
                walletMasterDto.setId(paymentId);
                walletMasterDto.setTimeCreated(timeCreated);
                walletMasterDto.setLastUpdated(lastUpdated);
                walletMasterDto.setUserProfileId(walletMaster.getUserProfile().getId());

                walletMasterDto.setOutletsId(walletMaster.getOutlets().getId());
                walletMasterDto.setOutletsName(walletMaster.getOutlets().getOutletName());
                
                walletMasterDto.setUserProfileId(walletMaster.getUserProfile().getId());
                walletMasterDto.setUserProfileFirstName(walletMaster.getUserProfile().getFirstName());
                walletMasterDto.setUserProfileLastName(walletMaster.getUserProfile().getLastName());
                
                return walletMasterDto;

            }).forEach(walletMasterDto -> {
                walletMasterDtos.add(walletMasterDto);

            });
        }
        return walletMasterDtos;
    }


    public WalletMaster dtoToEntity(WalletMasterDto dto) {
        WalletMaster walletMaster = new WalletMaster();
        
        walletMaster.setId(dto.getId());
        walletMaster.setTimeCreated(dto.getTimeCreated());
        walletMaster.setLastUpdated(dto.getLastUpdated());

        walletMaster.setTotalAmountAvailable(dto.getTotalAmountAvailable());

        UserProfile userProfile = userProfileRepository.findById(dto.getUserProfileId()).orElse(null);
        walletMaster.setUserProfile(userProfile);

        Outlets outlets = outletsRepository.findById(dto.getOutletsId()).orElse(null);
        walletMaster.setOutlets(outlets);

        walletMaster = walletMasterRepository.save(walletMaster);

        return walletMaster;
    }
}