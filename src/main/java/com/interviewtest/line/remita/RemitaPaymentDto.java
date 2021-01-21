
package com.interviewtest.line.remita;
import lombok.Data;

/**
 * @author Oki-Peter
 */
@Data
public class RemitaPaymentDto {

    private Long walletMasterId;

    private Double amountPaid;

    private String feeDescription;

    private String merchantId;

    private String serviceTypeId;

    private String payerEmail;

    private String payerPhone;

    private String payerName;
}
