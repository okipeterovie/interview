/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.interviewtest.line.controller;

import com.interviewtest.line.dto.WalletMasterDto;
import com.interviewtest.line.entity.WalletMaster;
import com.interviewtest.line.repository.WalletMasterRepository;
import com.interviewtest.line.response.JsonResponse;
import com.interviewtest.line.services.WalletMasterService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author G7
 */

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/payment-master")
public class WalletMasterController {

    @Autowired
    private WalletMasterService walletMasterService;

    @Autowired
    private WalletMasterRepository walletMasterRepository;


    @GetMapping(path = "/find/all")
    public ResponseEntity<Object> findAllPayments() {
        List<WalletMaster> walletMasterList = walletMasterRepository.findAll();
        List<WalletMasterDto> resultsDto = new ArrayList<>();

        if (walletMasterList != null) {
            walletMasterList.forEach(walletMaster -> {
                WalletMasterDto walletMasterDto = new WalletMasterDto();
                walletMasterDto.setId(walletMaster.getId());
                walletMasterDto.setTimeCreated(walletMaster.getTimeCreated());
                walletMasterDto.setLastUpdated(walletMaster.getLastUpdated());
                walletMasterDto.setLastUpdated(walletMaster.getLastUpdated());
                walletMasterDto.setUserProfileId(walletMaster.getUserProfile().getId());
                walletMasterDto.setUserProfileFirstName(walletMaster.getUserProfile().getFirstName());
                walletMasterDto.setUserProfileLastName(walletMaster.getUserProfile().getLastName());
                walletMasterDto.setOutletsId(walletMaster.getOutlets().getId());
                walletMasterDto.setOutletsName(walletMaster.getOutlets().getOutletName());

                resultsDto.add(walletMasterDto);
            });
        }
        return ResponseEntity.ok(new JsonResponse("See Data Object for Details", resultsDto));
    }

}
