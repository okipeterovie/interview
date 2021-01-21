package com.interviewtest.line.remita;

import com.google.gson.Gson;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Oki-Peter
 */
@Data
@Service
public class RemitaService {

    @Value("${remita-test-mode}")
    private Boolean remitaTestMode;

    private static final Logger logger = Logger.getLogger(RemitaService.class.getName());

    /**
     * @param remittaModel
     * @return
     */
    public RemitaResponse remitaPaymentHandle(RemittaModel remittaModel) {
        String hash = remittaModel.createHashString(remitaTestMode);
        String uri, serviceTypeId, beneficiaryName, beneficiaryAccount,
                beneficiaryBankCode, beneficiaryName3Line, beneficiaryAccount3Line,
                bankCode3Line, beneficiaryNameRemita, beneficiaryAccountRemita, bankCodeRemita,
                merchantId;
        Integer amount;
        if (remitaTestMode) {
            uri = RemitaPaymentParams.DemoConfig.gatewayUrl2;
            serviceTypeId = RemitaPaymentParams.DemoConfig.serviceTypeId2;
            amount = RemitaPaymentParams.DemoConfig.amount;
            beneficiaryName = RemitaPaymentParams.DemoConfig.beneficiaryName;
            beneficiaryAccount = RemitaPaymentParams.DemoConfig.beneficiaryAccount;
            beneficiaryBankCode = RemitaPaymentParams.DemoConfig.beneficiaryBankCode;
            beneficiaryName3Line = RemitaPaymentParams.DemoConfig.beneficiaryName3Line;
            beneficiaryAccount3Line = RemitaPaymentParams.DemoConfig.beneficiaryAccount3Line;
            bankCode3Line = RemitaPaymentParams.DemoConfig.bankCode3Line;
            beneficiaryNameRemita = RemitaPaymentParams.DemoConfig.beneficiaryNameRemita;
            beneficiaryAccountRemita = RemitaPaymentParams.DemoConfig.beneficiaryAccountRemita;
            bankCodeRemita = RemitaPaymentParams.DemoConfig.bankCodeRemita;
            merchantId = RemitaPaymentParams.DemoConfig.merchantId;
        } else {
            uri = RemitaPaymentParams.LiveConfig.gatewayUrl;
            serviceTypeId = RemitaPaymentParams.LiveConfig.serviceTypeId2;
            amount = RemitaPaymentParams.LiveConfig.amount;
            beneficiaryName = RemitaPaymentParams.LiveConfig.beneficiaryName;
            beneficiaryAccount = RemitaPaymentParams.LiveConfig.beneficiaryAccount;
            beneficiaryBankCode = RemitaPaymentParams.LiveConfig.beneficiaryBankCode;
            beneficiaryName3Line = RemitaPaymentParams.LiveConfig.beneficiaryName3Line;
            beneficiaryAccount3Line = RemitaPaymentParams.LiveConfig.beneficiaryAccount3Line;
            bankCode3Line = RemitaPaymentParams.LiveConfig.bankCode3Line;
            beneficiaryNameRemita = RemitaPaymentParams.LiveConfig.beneficiaryNameRemita;
            beneficiaryAccountRemita = RemitaPaymentParams.LiveConfig.beneficiaryAccountRemita;
            bankCodeRemita = RemitaPaymentParams.LiveConfig.bankCodeRemita;
            merchantId = RemitaPaymentParams.LiveConfig.merchantId;
        }

        RestTemplate restTemplate = new RestTemplate();
        // Add the Jackson message converter
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        Double amountPayable = remittaModel.getAmountPaid();
        Double LineShare = get3LineShare(amountPayable);
        Double remitaShare = getRemitaShare(amountPayable);
        Double actualTotal = amountPayable + LineShare + remitaShare;

        logger.log(Level.INFO, "here is the total: {0} > {1} > {2}", new Object[]{actualTotal, LineShare, remitaShare});
        
        RemitaRequest remitaRequest;
        if (Objects.equals(amountPayable, Double.valueOf(amount))) {
            remitaRequest = new RemitaRequest();
            remitaRequest.setServiceTypeId(serviceTypeId);
            remitaRequest.setAmount(actualTotal);
            remitaRequest.setOrderId(remittaModel.getOrderId());
            remitaRequest.setPayerName(remittaModel.getPayerName().replaceAll("[^a-zA-Z]+", ""));
            remitaRequest.setPayerEmail(remittaModel.getPayerEmail());
            remitaRequest.setPayerPhone(remittaModel.getPayerPhone());
            remitaRequest.setDescription(remittaModel.getFeeDescription());
            remitaRequest.getLineItems().add(new RemitaLineItem("45454", beneficiaryName, beneficiaryAccount, beneficiaryBankCode, 500.0, 1));
            remitaRequest.getLineItems().add(new RemitaLineItem("34444160119152814", beneficiaryName3Line, beneficiaryAccount3Line, bankCode3Line, LineShare, 0));
            remitaRequest.getLineItems().add(new RemitaLineItem("3555160119152814", beneficiaryNameRemita, beneficiaryAccountRemita, bankCodeRemita, remitaShare, 0));
        } 
        else {
            remitaRequest = new RemitaRequest();
            remitaRequest.setServiceTypeId(serviceTypeId);
            remitaRequest.setAmount(actualTotal);
            remitaRequest.setOrderId(remittaModel.getOrderId());
            remitaRequest.setPayerName(remittaModel.getPayerName().replaceAll("[^a-zA-Z]+", ""));
            remitaRequest.setPayerEmail(remittaModel.getPayerEmail());
            remitaRequest.setPayerPhone(remittaModel.getPayerPhone());
            remitaRequest.setDescription(remittaModel.getFeeDescription());
            remitaRequest.getLineItems().add(new RemitaLineItem("45454", beneficiaryName, beneficiaryAccount, beneficiaryBankCode, amountPayable, 1));
            remitaRequest.getLineItems().add(new RemitaLineItem("34444160119152814", beneficiaryName3Line, beneficiaryAccount3Line, bankCode3Line, LineShare, 0));
            remitaRequest.getLineItems().add(new RemitaLineItem("3555160119152814", beneficiaryNameRemita, beneficiaryAccountRemita, bankCodeRemita, remitaShare, 0));
        }
        
        Gson gson = new Gson();
        String input = gson.toJson(remitaRequest);
        
        // set headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "remitaConsumerKey=" + merchantId + ",remitaConsumerToken=" + hash);

        // send request and parse result
        HttpEntity<String> entity = new HttpEntity<>(input, headers);
        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, entity, String.class);

        logger.log(Level.INFO, "Remita Response >>>> {0}", response);
        logger.log(Level.INFO, "Remita Response Body >>>> {0}", response.getBody());
        
        String json = response.getBody().replace("jsonp (", "").replace(")", "").replace(",\"responseParams\":{}", "").trim();
        RemitaResponse remitaResponse = gson.fromJson(json, RemitaResponse.class);
        
        return remitaResponse;
    }

    public static Double get3LineShare(Double totalAmount) {
        Double amount = 0.0;
//     if(totalAmount < BeneficiaryConstants.REMITA_MAX_AMOUNT.maxAmount){
//         amount = BeneficiaryConstants.3Line_CUT_AMOUNT.maxAmount*BeneficiaryConstants.FLAT_CHARGE.maxAmount;//70% OF 50 NAIRA
//     }
//     else if(totalAmount >=  BeneficiaryConstants.REMITA_MAX_AMOUNT.maxAmount){ //if above 160000 take 0.75 of the total amount then divide it for 3Line(70) and remita(30)
//    	  amount = BeneficiaryConstants.REMITA_DIVISION_PERCENTAGE.maxAmount * totalAmount;
//    	  amount = BeneficiaryConstants.3Line_CUT_AMOUNT.maxAmount*amount;
//     }
        return amount;
    }

    public static Double getRemitaShare(Double totalAmount) {
        Double amount = 0.0;
//      if(totalAmount < BeneficiaryConstants.REMITA_MAX_AMOUNT.maxAmount){
//          amount = BeneficiaryConstants.REMITA_CUT_AMOUNT.maxAmount*BeneficiaryConstants.FLAT_CHARGE.maxAmount;
//      }
//      else if(totalAmount >=  BeneficiaryConstants.REMITA_MAX_AMOUNT.maxAmount){//if above 160000 take 0.75 of the total amount then divide it for 3Line(70) and remita(30)
//     	  amount = BeneficiaryConstants.REMITA_DIVISION_PERCENTAGE.maxAmount * totalAmount;
//     	  amount = BeneficiaryConstants.REMITA_CUT_AMOUNT.maxAmount*amount;
//      }
        return amount;
    }

    public RemittaModel updateRemittaModel(RemittaModel rm, String remitaData) {
        String newConcatStringFinalizeInit;
        if (getRemitaTestMode()) {
            rm.setApiKey(RemitaPaymentParams.DemoConfig.apiKey);
            rm.setMerchantId(RemitaPaymentParams.DemoConfig.merchantId);
            rm.setServiceTypeId(RemitaPaymentParams.DemoConfig.serviceTypeId);
            rm.setGatewayUrl(RemitaPaymentParams.DemoConfig.gatewayUrl);
            newConcatStringFinalizeInit = RemitaPaymentParams.DemoConfig.merchantId + remitaData + RemitaPaymentParams.DemoConfig.apiKey;
        } else {
            rm.setApiKey(RemitaPaymentParams.LiveConfig.apiKey);
            rm.setMerchantId(RemitaPaymentParams.LiveConfig.merchantId);
            rm.setServiceTypeId(RemitaPaymentParams.LiveConfig.serviceTypeId);
            rm.setGatewayUrl(RemitaPaymentParams.LiveConfig.gatewayUrl);
            newConcatStringFinalizeInit = rm.getMerchantId() + remitaData + rm.getApiKey(); //307
        }
        try {
            String hashToInitPost = HashUtil.getHash(newConcatStringFinalizeInit, HashUtil.HashType.SHA512);
            rm.setHashInit(hashToInitPost);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        return rm;
    }

    /**
     * @param orderID
     * @param RRR
     * @return
     */
    public String getHash(String orderID, String RRR) {
        String concatString;
        if (getRemitaTestMode()) {
            concatString = orderID + RemitaPaymentParams.DemoConfig.apiKey + RemitaPaymentParams.DemoConfig.merchantId;
        } else {
            concatString = orderID + RemitaPaymentParams.LiveConfig.apiKey + RemitaPaymentParams.LiveConfig.merchantId;
        }
        try {
            return HashUtil.getHash(concatString, HashUtil.HashType.SHA512);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            Logger.getLogger(RemitaService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * @param url2
     * @return 
     */
    public RemitaResponse installAllTrustingCertificateAndGetRemitaResponse(String url2) {
        HttpURLConnection httpURLConnection = null;
        URL url = null;
        try {
            /* Start of Fix */
            TrustManager[] trustAllCerts2 = new X509TrustManager[]{new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
                
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }
                
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
                
            }};
            
            SSLContext sc2 = SSLContext.getInstance("SSL");
            sc2.init(null, trustAllCerts2, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc2.getSocketFactory());
            
            // Create all-trusting host name verifier
            HostnameVerifier allHostsValid2 = new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };
            // Install the all-trusting host verifier
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid2);
            
            url = new URL(url2);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);

            //To get response
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
            String response2 = null;
            StringBuilder builder = new StringBuilder();
            while ((response2 = bufferedReader.readLine()) != null) {
                builder.append(response2);
            }
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
            RemitaResponse remRes = HashUtil.fromJson(builder.toString(), RemitaResponse.class);
            return remRes;
        } catch (NoSuchAlgorithmException | KeyManagementException | IOException ex) {
            Logger.getLogger(RemitaService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
