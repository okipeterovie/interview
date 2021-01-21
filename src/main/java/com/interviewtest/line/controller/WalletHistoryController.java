package com.interviewtest.line.controller;

import com.interviewtest.line.dto.WalletHistoryDto;
import com.interviewtest.line.entity.WalletHistory;
import com.interviewtest.line.repository.WalletHistoryRepository;
import com.interviewtest.line.response.JsonResponse;
import com.interviewtest.line.services.WalletHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/wallet-history")
public class WalletHistoryController {

    @Autowired
    WalletHistoryService walletHistoryService;

    @Autowired
    WalletHistoryRepository walletHistoryRepository;

    //save wallet details/history

//    @PostMapping("/save")
//    public ResponseEntity<Object> saveProgressReport(@RequestBody WalletHistoryDto walletHistoryDto) {
//        WalletHistory walletHistory = walletHistoryService.dtoToEntity(walletHistoryDto);
//
//
//        return ResponseEntity.ok(new JsonResponse("See Data Object for Details!", walletHistory));
//    }

        @GetMapping("/receipt/{RRR}")
    public ResponseEntity<Object> findFirstByRemitaNumber(@PathVariable String RRR) {
            List<WalletHistory> walletHistoryList =
                    walletHistoryRepository.findByRRR(RRR);
            List<WalletHistoryDto> resultsDto = new ArrayList<>();
            if (walletHistoryList!=null){
                walletHistoryList.forEach((walletHistory) ->{

                    WalletHistoryDto walletHistoryDto = new WalletHistoryDto();
                    walletHistoryDto.setId(walletHistory.getId());
                    walletHistoryDto.setAmountPaid(walletHistory.getAmountPaid());
                    walletHistoryDto.setPaymentDate(walletHistory.getPaymentDate());
                    walletHistoryDto.setPaymentStatus(walletHistory.getPaymentStatus().toString());
                    walletHistoryDto.setUsageStatus(walletHistory.getUsageStatus());
                    walletHistoryDto.setTeller(walletHistory.getTeller());
                    walletHistoryDto.setTellerPaymentDate(walletHistory.getTellerPaymentDate());
                    walletHistoryDto.setBankOrMerchantId(walletHistory.getBankOrMerchantId());
                    walletHistoryDto.setFeeDescription(walletHistory.getFeeDescription());
                    walletHistoryDto.setUnitAmount(walletHistory.getUnitAmount());
                    walletHistoryDto.setChannel(walletHistory.getChannel());
                    walletHistoryDto.setChannelIdentifier(walletHistory.getChannelIdentifier());
                    walletHistoryDto.setPaymentSwitchSessionId(walletHistory.getPaymentSwitchSessionId());
                    walletHistoryDto.setPaymentNameFromBank(walletHistory.getPaymentNameFromBank());
                    walletHistoryDto.setSurchargeInKobo(walletHistory.getSurchargeInKobo());
                    walletHistoryDto.setEBillsPayTransactionCompleteDate(walletHistory.getEBillsPayTransactionCompleteDate());
                    walletHistoryDto.setNotificationCounter(walletHistory.getNotificationCounter());
                    walletHistoryDto.setReceivedPaymentNotification(walletHistory.getReceivedPaymentNotification());
                    walletHistoryDto.setDateCreated(walletHistory.getDateCreated());
                    walletHistoryDto.setLastUpdated(walletHistory.getLastUpdated());
                    walletHistoryDto.setRRR(walletHistory.getRRR());
                    walletHistoryDto.setWalletMasterId(walletHistory.getWalletMaster().getId());
                    walletHistoryDto.setOrderId(walletHistory.getOrderId());
                    walletHistoryDto.setServiceTypeId(walletHistory.getServiceTypeId());
                    walletHistoryDto.setIsDeleted(walletHistory.getIsDeleted());
                    walletHistoryDto.setBankOrMerchantId(walletHistory.getBankOrMerchantId());
                    walletHistoryDto.setTransactionId(walletHistory.getTransactionId());
                    walletHistoryDto.setPayerEmail(walletHistory.getPayerEmail());
                    walletHistoryDto.setPayerPhone(walletHistory.getPayerPhone());

                    resultsDto.add(walletHistoryDto);
                });
            }
            return ResponseEntity.ok(new JsonResponse("See Data Object for details", resultsDto));
        }
    @GetMapping(path="/find/all")
    public ResponseEntity <Object> findwalletHistoryList(){
        List<WalletHistory> walletHistoryList =
                walletHistoryRepository.findAll();
        List<WalletHistoryDto> resultsDto = new ArrayList<>();
        if (walletHistoryList!=null){
            walletHistoryList.forEach((walletHistory) ->{

                WalletHistoryDto walletHistoryDto = new WalletHistoryDto();
                walletHistoryDto.setId(walletHistory.getId());
                walletHistoryDto.setAmountPaid(walletHistory.getAmountPaid());
                walletHistoryDto.setPaymentDate(walletHistory.getPaymentDate());
                walletHistoryDto.setPaymentStatus(walletHistory.getPaymentStatus().toString());
                walletHistoryDto.setUsageStatus(walletHistory.getUsageStatus());
                walletHistoryDto.setTeller(walletHistory.getTeller());
                walletHistoryDto.setTellerPaymentDate(walletHistory.getTellerPaymentDate());
                walletHistoryDto.setBankOrMerchantId(walletHistory.getBankOrMerchantId());
                walletHistoryDto.setFeeDescription(walletHistory.getFeeDescription());
                walletHistoryDto.setUnitAmount(walletHistory.getUnitAmount());
              //  walletHistoryDto.setReferenceId(walletHistory.getReferenceId());
                walletHistoryDto.setChannel(walletHistory.getChannel());
                walletHistoryDto.setChannelIdentifier(walletHistory.getChannelIdentifier());

                walletHistoryDto.setPaymentSwitchSessionId(walletHistory.getPaymentSwitchSessionId());
                walletHistoryDto.setPaymentNameFromBank(walletHistory.getPaymentNameFromBank());
                walletHistoryDto.setSurchargeInKobo(walletHistory.getSurchargeInKobo());
                walletHistoryDto.setEBillsPayTransactionCompleteDate(walletHistory.getEBillsPayTransactionCompleteDate());
                walletHistoryDto.setNotificationCounter(walletHistory.getNotificationCounter());
                walletHistoryDto.setReceivedPaymentNotification(walletHistory.getReceivedPaymentNotification());
                walletHistoryDto.setDateCreated(walletHistory.getDateCreated());
                walletHistoryDto.setLastUpdated(walletHistory.getLastUpdated());
                walletHistoryDto.setRRR(walletHistory.getRRR());
                walletHistoryDto.setWalletMasterId(walletHistory.getWalletMaster().getId());
                walletHistoryDto.setOrderId(walletHistory.getOrderId());
                walletHistoryDto.setServiceTypeId(walletHistory.getServiceTypeId());
                walletHistoryDto.setIsDeleted(walletHistory.getIsDeleted());
                walletHistoryDto.setBankOrMerchantId(walletHistory.getBankOrMerchantId());
                walletHistoryDto.setTransactionId(walletHistory.getTransactionId());
                walletHistoryDto.setPayerEmail(walletHistory.getPayerEmail());
                walletHistoryDto.setPayerPhone(walletHistory.getPayerPhone());

                resultsDto.add(walletHistoryDto);
            });
        }
        return ResponseEntity.ok(new JsonResponse("See Data Object for details", resultsDto));
    }

    @GetMapping(path="/organization/find/wallet-master-id/{id}")
    public ResponseEntity <Object> findWalletHistoryListByWalletMasterId(@PathVariable Long id){
        List<WalletHistory> walletHistoryList =
                walletHistoryRepository.findByWalletMasterId(id);
        List<WalletHistoryDto> resultsDto = new ArrayList<>();
        if (walletHistoryList!=null){
            walletHistoryList.forEach((walletHistory) ->{

                WalletHistoryDto walletHistoryDto = new WalletHistoryDto();
                walletHistoryDto.setId(walletHistory.getId());
                walletHistoryDto.setAmountPaid(walletHistory.getAmountPaid());
                walletHistoryDto.setPaymentDate(walletHistory.getPaymentDate());
                walletHistoryDto.setPaymentStatus(walletHistory.getPaymentStatus().toString());
                walletHistoryDto.setUsageStatus(walletHistory.getUsageStatus());
                walletHistoryDto.setTeller(walletHistory.getTeller());
                walletHistoryDto.setTellerPaymentDate(walletHistory.getTellerPaymentDate());
                walletHistoryDto.setBankOrMerchantId(walletHistory.getBankOrMerchantId());
                walletHistoryDto.setFeeDescription(walletHistory.getFeeDescription());
                walletHistoryDto.setUnitAmount(walletHistory.getUnitAmount());
//                walletHistoryDto.setReferenceId(walletHistory.getReferenceId());
                walletHistoryDto.setChannel(walletHistory.getChannel());
                walletHistoryDto.setChannelIdentifier(walletHistory.getChannelIdentifier());
                walletHistoryDto.setPaymentSwitchSessionId(walletHistory.getPaymentSwitchSessionId());
                walletHistoryDto.setPaymentNameFromBank(walletHistory.getPaymentNameFromBank());
                walletHistoryDto.setSurchargeInKobo(walletHistory.getSurchargeInKobo());
                walletHistoryDto.setEBillsPayTransactionCompleteDate(walletHistory.getEBillsPayTransactionCompleteDate());
                walletHistoryDto.setNotificationCounter(walletHistory.getNotificationCounter());
                walletHistoryDto.setReceivedPaymentNotification(walletHistory.getReceivedPaymentNotification());
                walletHistoryDto.setDateCreated(walletHistory.getDateCreated());
                walletHistoryDto.setLastUpdated(walletHistory.getLastUpdated());
                walletHistoryDto.setRRR(walletHistory.getRRR());
                walletHistoryDto.setWalletMasterId(walletHistory.getWalletMaster().getId());
                walletHistoryDto.setOrderId(walletHistory.getOrderId());
                walletHistoryDto.setServiceTypeId(walletHistory.getServiceTypeId());
                walletHistoryDto.setIsDeleted(walletHistory.getIsDeleted());
                walletHistoryDto.setBankOrMerchantId(walletHistory.getBankOrMerchantId());
                walletHistoryDto.setTransactionId(walletHistory.getTransactionId());
                walletHistoryDto.setPayerEmail(walletHistory.getPayerEmail());
                walletHistoryDto.setPayerPhone(walletHistory.getPayerPhone());

                resultsDto.add(walletHistoryDto);
            });
        }
        return ResponseEntity.ok(new JsonResponse("See Data Object for details", resultsDto));
    }


}
