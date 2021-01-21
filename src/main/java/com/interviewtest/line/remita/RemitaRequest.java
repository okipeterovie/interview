
package com.interviewtest.line.remita;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ademola Aina
 */
@NoArgsConstructor
@Data
public class RemitaRequest {
    
    private String serviceTypeId;
    
    private Double amount;
    
    private String orderId;
    
    private String payerName;
    
    private String payerEmail;
    
    private String payerPhone;
    
    private String description;
    
    private List<RemitaLineItem> lineItems = new ArrayList<>();
        
}
