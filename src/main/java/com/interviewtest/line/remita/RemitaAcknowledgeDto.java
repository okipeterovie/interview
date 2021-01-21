
package com.interviewtest.line.remita;

import lombok.Data;

import java.time.LocalDate;

/**
 * @author Oki-Peter
 */
@Data
public class RemitaAcknowledgeDto {
 
     private String rrr;
     
     private String orderId;

     private Long paymentMasterId;

     private Double totalAmountDue;

     private Long entityId;

     private String entityName;

     private String feeDescription;

     private String returnBackUrl;

     private LocalDate newDueDate;

     private Double amountPaid;

     private Double balance;

     private String merchantId;

     private Double totalAmountRecovered;

     private String serviceTypeId;

     private String transactionId;

     private String payerEmail;

     private String payerPhone;

     private String payerName;

}
