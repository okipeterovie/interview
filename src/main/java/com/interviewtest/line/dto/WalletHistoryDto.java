/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.interviewtest.line.dto;


import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 *
 * @author Oki-PEter
 */
@Data
public class WalletHistoryDto {

        private Long id;

        @Enumerated(value=EnumType.STRING)
        private String paymentStatus;

        private Double amountPaid;

        private Date paymentDate;

        private String usageStatus;

        private String teller;

        private String bankOrMerchantId;

        private Date tellerPaymentDate;

        private String feeDescription;

        private Double unitAmount;

        private String referenceId;

        private String transactionId;

        private String channelIdentifier;

        private String paymentSwitchSessionId;

        private String RRR;

        private String paymentNameFromBank;

        private Long surchargeInKobo;

        private String channel;

        private LocalDate eBillsPayTransactionCompleteDate;

        private Integer notificationCounter;

        private Boolean receivedPaymentNotification;

        private Date dateCreated;

        private LocalDateTime lastUpdated;

        private Boolean isDeleted;

        private Long walletMasterId;

        private String serviceTypeId;

        private String orderId;

        private String payerEmail;

        private String payerPhone;


}
