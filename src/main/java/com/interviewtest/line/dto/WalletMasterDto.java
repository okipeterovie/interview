/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.interviewtest.line.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 *
 * @author Oki-Peter
 */
@Data
public class WalletMasterDto {
    
    
    private Long id;

    private LocalDateTime timeCreated;
    
    private LocalDateTime lastUpdated;

    private Double totalAmountAvailable;

    private Long outletsId;

    private String outletsName;

    private Long userProfileId;

    private String userProfileFirstName;

    private String userProfileLastName;

    private String userProfileEmail;

}
