package com.interviewtest.line.remita;

import lombok.Data;

/**
 * @author Ademola Aina
 */
@Data
public class RemitaResponse {

    public String orderId;
    public String RRR;
    public double amount;

    public String paymentDate;
    public String status;
    public String statuscode;
    public String statusMessage;
    public String uniqueReference;
    public String message;
}
