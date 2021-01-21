package com.interviewtest.line.remita;

import lombok.Data;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Ademola Aina
 */
@Data
public class RemittaModel {

    private String payerName;
    private String payerEmail;
    private String payerPhone;
    private Double amountPaid;
    private String paymentType;
    private String feeDescription;
    
    //*******************************
    
    private String merchantId;
    private String serviceTypeId;
    private String responseUrl;
    private String apiKey;
    private String hash;
    private String gatewayUrl;
    private String orderId;
    private String rrr;
    private String hashInit;
    private String dateCreated;

    public RemittaModel() {
        orderId = uniqueRef();
    }

    
    /**
     * @param remitaTestMode
     * @return 
     */
    public String createHashString(boolean remitaTestMode) {
        String concatStringFinalize;
        if (remitaTestMode) {
            concatStringFinalize = RemitaPaymentParams.DemoConfig.merchantId
                    + RemitaPaymentParams.DemoConfig.serviceTypeId
                    + orderId
                    + amountPaid
                    + RemitaPaymentParams.DemoConfig.apiKey;
        } else {
            concatStringFinalize = RemitaPaymentParams.LiveConfig.merchantId
                    + RemitaPaymentParams.LiveConfig.serviceTypeId
                    + orderId
                    + amountPaid
                    + RemitaPaymentParams.LiveConfig.apiKey;
        }
        try {
            hash = HashUtil.getHash(concatStringFinalize, HashUtil.HashType.SHA512);
            return hash;
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            Logger.getLogger(RemittaModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    

    public static String getSerial() {
        Random rand = new Random();
        String concat = Integer.toString(rand.nextInt(1000)) + Integer.toString(rand.nextInt(1000)) + rand.nextInt(1000) + System.currentTimeMillis();
        StringBuilder transc_id = new StringBuilder();
        transc_id.append(concat);
        return transc_id.toString();
    }

    public static String uniqueRef() {
        String unique = (String) UUID.randomUUID().toString().subSequence(0, 4);
        String unique2 = (String) UUID.randomUUID().toString().subSequence(0, 4);
        String unique3 = (String) UUID.randomUUID().toString().subSequence(0, 4);
        String unique4 = (String) UUID.randomUUID().toString().subSequence(0, 5);
        String ref = unique.toUpperCase() + "-" + unique2.toUpperCase() + "-" + unique3.toUpperCase() + "-" + unique4.toUpperCase();
        return ref;
    }

    public static String uniqueRef1() {
        String unique = (String) UUID.randomUUID().toString().subSequence(0, 4);
        String unique2 = (String) UUID.randomUUID().toString().subSequence(0, 4);
        String unique3 = (String) UUID.randomUUID().toString().subSequence(0, 5);
        String unique4 = (String) UUID.randomUUID().toString().subSequence(0, 4);
        String ref = unique2.toUpperCase() + "-" + unique.toUpperCase() + "-" + unique4.toUpperCase() + "-" + unique3.toUpperCase();
        return ref;
    }

    public static String uniqueRef2() {
        String unique = (String) UUID.randomUUID().toString().subSequence(0, 4);
        String unique2 = (String) UUID.randomUUID().toString().subSequence(0, 5);
        String unique3 = (String) UUID.randomUUID().toString().subSequence(0, 4);
        String unique4 = (String) UUID.randomUUID().toString().subSequence(0, 4);
        String ref = unique3.toUpperCase() + "-" + unique4.toUpperCase() + "-" + unique.toUpperCase() + "-" + unique2.toUpperCase();
        return ref;
    }

    public static boolean isNumeric(String str) {
        char[] var1 = str.toCharArray();
        int var2 = var1.length;

        for (int var3 = 0; var3 < var2; ++var3) {
            char c = var1[var3];
            if (!Character.isDigit(c)) {
                return false;
            }
        }

        return true;
    }

}
