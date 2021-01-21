
package com.interviewtest.line.remita;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ademola Aina
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RemitaLineItem {
    
    private String lineItemsId;
    
    private String beneficiaryName;
    
    private String beneficiaryAccount;
    
    private String bankCode;
    
    private Double beneficiaryAmount;
    
    private Integer deductFeeFrom;
    
}
