package com.interviewtest.line.remita;

import com.interviewtest.line.controller.WalletMasterController;
import com.interviewtest.line.entity.UserProfile;
import com.interviewtest.line.entity.UsersAccount;
import com.interviewtest.line.entity.WalletHistory;
import com.interviewtest.line.entity.WalletMaster;
import com.interviewtest.line.enumeration.PaymentStatus;
import com.interviewtest.line.managers.UserManagement;
import com.interviewtest.line.repository.UserProfileRepository;
import com.interviewtest.line.repository.WalletHistoryRepository;
import com.interviewtest.line.repository.WalletMasterRepository;
import com.interviewtest.line.response.JsonResponse;
import com.interviewtest.line.services.WalletMasterService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Oki-PEter
 */
@Data
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/payment/remita")
public class RemitaController {

    @Autowired
    private WalletHistoryRepository walletHistoryRepository;

    @Autowired
    WalletMasterController walletMasterController;

    @Autowired
    WalletMasterService walletMasterService;

    @Autowired
    WalletMasterRepository walletMasterRepository;

    @Autowired
    private RemitaService remitaService;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private UserManagement userManagement;


    private static final Logger logger = Logger.getLogger(RemitaController.class.getName());


    @PostMapping("/start-payment")
    public ResponseEntity<Object> paymentActivity(@RequestBody RemitaPaymentDto remitaPaymentDto,
                                                  HttpServletRequest request){
        //Create RemitaModel to return as response
        RemittaModel rm = new RemittaModel();

        rm.setPayerEmail(remitaPaymentDto.getPayerEmail());
//        rm.setResponseurl(remitaPaymentDto.getReturnBackUrl());
        rm.setAmountPaid(remitaPaymentDto.getAmountPaid());
        rm.setPaymentType("MASTERCARD");
        rm.setFeeDescription(remitaPaymentDto.getFeeDescription());
        rm.setPayerPhone(remitaPaymentDto.getPayerPhone());
        rm.setPayerName(remitaPaymentDto.getPayerName());


        //Set Certificate
        String rrrErrorMessage;
        // commented out because payment now starts at the frontend
//        if (remitaService.getRemitaTestMode()) {
//            HashUtil.trustCertificate(RemitaPaymentParams.DemoConfig.remitaFinalizeUrl);
//            rrrErrorMessage = RemitaPaymentParams.DemoConfig.rrrError;
//            request.setAttribute("remitaFinalize", RemitaPaymentParams.DemoConfig.remitaFinalizeUrl);
//        } else {
//            HashUtil.trustCertificate(RemitaPaymentParams.LiveConfig.remitaFinalizeUrl);
//            rrrErrorMessage = RemitaPaymentParams.LiveConfig.rrrError;
//            request.setAttribute("remitaFinalize", RemitaPaymentParams.LiveConfig.remitaFinalizeUrl);
//        }


        //Generate RRR
        RemitaResponse response = remitaService.remitaPaymentHandle(rm);
        if (!"025".equals(response.getStatuscode())) {
            logger.log(Level.INFO, "{0} --> {1}", new Object[]{response.getStatus(), response.getStatusMessage()});
            return new ResponseEntity<>(new JsonResponse(HttpStatus.BAD_REQUEST, response.getStatusMessage() + "[Attempted Amount: "+remitaPaymentDto.getAmountPaid()+"]"), HttpStatus.BAD_REQUEST);
        }

        //Update request
        request.setAttribute("statusCode", response.getStatuscode());
        request.setAttribute("rrr", response.getRRR());
        request.getSession().setAttribute("orderIdSession", rm.getOrderId());
        request.getSession().setAttribute("rrrFiling", response.getRRR());

        //Save Transaction
        WalletHistory walletTransactionHistory = new WalletHistory();
        walletTransactionHistory.setPaymentStatus(PaymentStatus.PENDING);

        walletTransactionHistory.setPaymentNameFromBank(rm.getPayerName());

        walletTransactionHistory.setTeller(response.getRRR());
        walletTransactionHistory.setRRR(response.getRRR());
        walletTransactionHistory.setReferenceId(rm.getOrderId());
        walletTransactionHistory.setAmountPaid(Double.valueOf(remitaPaymentDto.getAmountPaid().toString()));
        walletTransactionHistory.setFeeDescription(remitaPaymentDto.getFeeDescription());
        walletTransactionHistory.setDateCreated(new Date());
        walletTransactionHistory.setUsageStatus("NOT USED");
        walletTransactionHistory.setBankOrMerchantId("N/A");
        walletTransactionHistory.setServiceTypeId(rm.getServiceTypeId());
        walletTransactionHistory.setNotificationCounter(0);
        walletTransactionHistory.setReceivedPaymentNotification(false);
        walletTransactionHistory.setChannel("REMITA");
        walletTransactionHistory.setPayerEmail(remitaPaymentDto.getPayerEmail());
        walletTransactionHistory.setPayerPhone(remitaPaymentDto.getPayerPhone());

        UsersAccount usersAccount = userManagement.findByUsername(remitaPaymentDto.getPayerEmail());
        UserProfile userProfile = userProfileRepository.findByUsersAccount(usersAccount);

        WalletMaster walletMaster = walletMasterRepository.findByUserProfileId(userProfile.getId());

        walletTransactionHistory.setWalletMaster(walletMaster);
        walletTransactionHistory = walletHistoryRepository.save(walletTransactionHistory);

        //Update response model
        rm.setRrr(response.getRRR());
        rm.setDateCreated(walletTransactionHistory.getDateCreated().toString());
        rm.setMerchantId("N/A");
        rm = remitaService.updateRemittaModel(rm, response.getRRR());
        request.setAttribute("remitaFinalizeHash", rm.getHashInit());
        return ResponseEntity.ok(new JsonResponse("See Data Object for Details", rm));
    }


    public static String generateRandomNumber() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 57; // numeral '9'
        int targetStringLength = 18;
        Random random = new Random();
        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        //add hyphen
        StringBuilder builder = new StringBuilder(generatedString);
        builder.insert(4, '-');
        builder.insert(9, '-');
        builder.insert(15, '-');
        String hyphenatedString = builder.toString();

        return hyphenatedString;
    }

    @PostMapping("/remitta_acknowledge")
    public ResponseEntity<Object> processPayment(@RequestBody RemitaAcknowledgeDto remitaAcknowledgeDto,
            HttpServletRequest request,
            HttpServletResponse response
    ) {

        //Create RemitaModel to return as response
        RemittaModel rm = new RemittaModel();

        rm.setAmountPaid(remitaAcknowledgeDto.getAmountPaid());
        rm.setPaymentType("MASTERCARD");
        rm.setFeeDescription(remitaAcknowledgeDto.getFeeDescription());
        rm.setPayerName(remitaAcknowledgeDto.getPayerName());
        rm.setRrr(remitaAcknowledgeDto.getRrr());

        String RRR = rm.getRrr();
        String orderID = remitaAcknowledgeDto.getOrderId();

        if (RRR.equalsIgnoreCase("0000") || orderID.equalsIgnoreCase("0000") || "".equals(RRR) || "".equals(orderID)) {
            return new ResponseEntity<>(new JsonResponse(HttpStatus.BAD_REQUEST, "Payment processing not successful! Invalid RRR and/or OrderId"), HttpStatus.BAD_REQUEST);
        }

        String hash = remitaService.getHash(orderID, RRR);
        String url2;
        if (remitaService.getRemitaTestMode()) {
            url2 = RemitaPaymentParams.DemoConfig.checkStatusUrl + "/" + RemitaPaymentParams.DemoConfig.merchantId + "/" + orderID + "/" + hash + "/orderstatus.reg";
        } else {
            url2 = RemitaPaymentParams.LiveConfig.checkStatusUrl + "/" + RemitaPaymentParams.LiveConfig.merchantId + "/" + orderID + "/" + hash + "/orderstatus.reg";
        }

        RemitaResponse remRes = remitaService.installAllTrustingCertificateAndGetRemitaResponse(url2);
        if (remRes == null || !(remRes instanceof RemitaResponse)) {
            return new ResponseEntity<>(new JsonResponse(HttpStatus.BAD_REQUEST, "Something went wrong while processing response from remitta"), HttpStatus.OK);
        }

        WalletHistory walletTransactionHistory = walletHistoryRepository.findFirstByTellerOrderByIdDesc(RRR);

        if ("APPROVED".equals(walletTransactionHistory.getPaymentStatus().toString()) && walletTransactionHistory.getUsageStatus().trim().equalsIgnoreCase("USED")) {
            logger.log(Level.INFO, "Payment already processed....1 {0} usage {1}", new Object[]{walletTransactionHistory.getPaymentStatus(), walletTransactionHistory.getUsageStatus()});
            return ResponseEntity.ok(new JsonResponse("Payment already processed!", walletTransactionHistory));
        }

        logger.log(Level.INFO, "Remita Response >>>> {0}", remRes);
        logger.log(Level.INFO, "Status Code >>>> {0}", remRes.statuscode);
        logger.log(Level.INFO, "Status >>>> {0}", remRes.status);

        if (remRes.getStatus().equalsIgnoreCase(RemitaPaymentParams.LiveConfig.remita_00)
                || remRes.getStatus().equalsIgnoreCase(RemitaPaymentParams.LiveConfig.remita_01)
                || remRes.getStatus().equalsIgnoreCase(RemitaPaymentParams.LiveConfig.remita_021)
                || remRes.getStatus().equalsIgnoreCase(RemitaPaymentParams.LiveConfig.remita_045)) {
            logger.info("State IRS transaction successful");
            walletTransactionHistory.setPaymentStatus(PaymentStatus.APPROVED);

            walletTransactionHistory.setPaymentNameFromBank(rm.getPayerName());
            walletTransactionHistory.setRRR(remRes.getRRR());
            walletTransactionHistory.setPaymentDate(new Date());
            walletTransactionHistory.setUsageStatus("USED");
            walletTransactionHistory.setTeller(remRes.getRRR());
            walletTransactionHistory.setBankOrMerchantId("N/A");
            walletTransactionHistory.setTellerPaymentDate(new Date());
            walletTransactionHistory.setReferenceId(remRes.getOrderId());
            walletTransactionHistory.setAmountPaid(remRes.getAmount());
            walletTransactionHistory.setOrderId(remRes.getOrderId());
            walletTransactionHistory.setServiceTypeId(remitaAcknowledgeDto.getServiceTypeId());


            walletTransactionHistory.setBankOrMerchantId(remitaAcknowledgeDto.getMerchantId());
            walletTransactionHistory.setTransactionId(remitaAcknowledgeDto.getTransactionId());

            walletTransactionHistory.setFeeDescription(remitaAcknowledgeDto.getFeeDescription());
            walletTransactionHistory.setChannel("REMITA");
            walletTransactionHistory.setChannelIdentifier("REMITA");
            walletTransactionHistory.setEBillsPayTransactionCompleteDate(LocalDate.now());
            walletTransactionHistory.setNotificationCounter(+1);
            walletTransactionHistory.setReceivedPaymentNotification(true);



            if (walletTransactionHistory.getPaymentStatus() == PaymentStatus.APPROVED) {

                UsersAccount usersAccount = userManagement.findByUsername(remitaAcknowledgeDto.getPayerEmail());
                UserProfile userProfile = userProfileRepository.findByUsersAccount(usersAccount);

                WalletMaster walletMaster = walletMasterRepository.findByUserProfileId(userProfile.getId());
                Long walletMasterId = walletMaster.getId();

                WalletMaster walletMasterTable =
                        walletMasterService.getWalletMasterRepository().
                                findById(walletMasterId).orElse(walletTransactionHistory.getWalletMaster());

                //set new balance into payment master
                walletMasterTable.setId(walletMasterId);

                Double previousAmountAvailable = walletMasterTable.getTotalAmountAvailable();

                Double totalAmountAvailable = previousAmountAvailable + remRes.getAmount();

                walletMasterTable.setTotalAmountAvailable(totalAmountAvailable);

                walletMasterRepository.save(walletMasterTable);

            } else {
                logger.info("State IRS transaction unsuccessful");
                walletTransactionHistory.setPaymentStatus(PaymentStatus.DISAPPROVED);
                walletTransactionHistory.setPaymentDate(new Date());
                walletTransactionHistory.setUsageStatus("NOT_USED");
                walletTransactionHistory.setTeller(remRes.getRRR());
                walletTransactionHistory.setBankOrMerchantId("N/A");
                walletTransactionHistory.setTellerPaymentDate(new Date());
                walletTransactionHistory.setReferenceId(remRes.getOrderId());

            }
            walletTransactionHistory = walletHistoryRepository.save(walletTransactionHistory);

            }
        return ResponseEntity.ok(new JsonResponse("Saved Successfully!", walletTransactionHistory));

    }


}
