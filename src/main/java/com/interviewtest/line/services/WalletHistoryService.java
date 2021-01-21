package com.interviewtest.line.services;

import com.interviewtest.line.dto.WalletHistoryDto;
import com.interviewtest.line.dto.WalletMasterDto;
import com.interviewtest.line.entity.WalletHistory;
import com.interviewtest.line.entity.WalletMaster;
import com.interviewtest.line.enumeration.PaymentStatus;
import com.interviewtest.line.repository.WalletHistoryRepository;
import com.interviewtest.line.repository.WalletMasterRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import static com.interviewtest.line.enumeration.PaymentStatus.APPROVED;


@Data
@Service
public class WalletHistoryService {

    
    @Autowired
    private WalletMasterRepository walletMasterRepository;

    @Autowired
    private WalletHistoryRepository walletHistoryRepository;

    private Logger logger = Logger.getLogger(WalletMasterService.class.getName());

    public List<WalletHistoryDto> findPaymentHistory(List<WalletHistory> walletHistory) {
        List<WalletHistoryDto> walletHistoryDtoArrayList = new ArrayList<>();
        if (walletHistory != null && walletHistory.isEmpty()) {
            walletHistory.stream().map((historyOfWallet) -> {

                Long walletHistoryId = historyOfWallet.getId();

                PaymentStatus paymentStatusType = historyOfWallet.getPaymentStatus();
                String paymentStatus = paymentStatusType.toString();

                Double amountPaid = historyOfWallet.getAmountPaid();

                Date paymentDate = historyOfWallet.getPaymentDate();
                String usageStatus = historyOfWallet.getUsageStatus();
                String teller = historyOfWallet.getTeller();
                String bankOrMerchantId = historyOfWallet.getBankOrMerchantId();
                Date tellerPaymentDate = historyOfWallet.getTellerPaymentDate();
                String feeDescription = historyOfWallet.getFeeDescription();
                Double unitAmount = historyOfWallet.getUnitAmount();
                String referenceId = historyOfWallet.getReferenceId();
                String channelIdentifier = historyOfWallet.getChannelIdentifier();

                String paymentSwitchSessionId = historyOfWallet.getPaymentSwitchSessionId();
                String paymentNameFromBank = historyOfWallet.getPaymentNameFromBank();
                Long surchargeInKobo = historyOfWallet.getSurchargeInKobo();
                String channel = historyOfWallet.getChannel();
                LocalDate eBillsPayTransactionCompleteDate = historyOfWallet.getEBillsPayTransactionCompleteDate();
                Integer notificationCounter = historyOfWallet.getNotificationCounter();
                Boolean receivedPaymentNotification = historyOfWallet.getReceivedPaymentNotification();
                Date dateCreated = historyOfWallet.getDateCreated();
                LocalDateTime lastUpdated = historyOfWallet.getLastUpdated();
                Boolean isDeleted = historyOfWallet.getIsDeleted();
                String rrr = historyOfWallet.getRRR();

                WalletHistoryDto walletHistoryDto = new WalletHistoryDto();
                walletHistoryDto.setId(walletHistoryId);

                walletHistoryDto.setPaymentStatus(paymentStatus);
                walletHistoryDto.setAmountPaid(amountPaid);

                walletHistoryDto.setPaymentDate(paymentDate);
                walletHistoryDto.setUsageStatus(usageStatus);
                walletHistoryDto.setTeller(teller);
                walletHistoryDto.setBankOrMerchantId(bankOrMerchantId);
                walletHistoryDto.setTellerPaymentDate(tellerPaymentDate);
                walletHistoryDto.setFeeDescription(feeDescription);
                walletHistoryDto.setUnitAmount(unitAmount);
                walletHistoryDto.setOrderId(referenceId);
                walletHistoryDto.setChannelIdentifier(channelIdentifier);

                walletHistoryDto.setPaymentSwitchSessionId(paymentSwitchSessionId);
                walletHistoryDto.setPaymentNameFromBank(paymentNameFromBank);
                walletHistoryDto.setSurchargeInKobo(surchargeInKobo);
                walletHistoryDto.setChannel(channel);
                walletHistoryDto.setEBillsPayTransactionCompleteDate(eBillsPayTransactionCompleteDate);
                walletHistoryDto.setNotificationCounter(notificationCounter);
                walletHistoryDto.setReceivedPaymentNotification(receivedPaymentNotification);
                walletHistoryDto.setDateCreated(dateCreated);
                walletHistoryDto.setLastUpdated(lastUpdated);
                walletHistoryDto.setIsDeleted(isDeleted);
                walletHistoryDto.setRRR(rrr);
                walletHistoryDto.setWalletMasterId(historyOfWallet.getWalletMaster().getId());
                walletHistoryDto.setOrderId(historyOfWallet.getOrderId());
                walletHistoryDto.setPayerEmail(historyOfWallet.getPayerEmail());
                walletHistoryDto.setPayerPhone(historyOfWallet.getPayerPhone());


                return walletHistoryDto;
            }).forEach(walletHistoryDto ->
            {
                walletHistoryDtoArrayList.add(walletHistoryDto);
            });
        }

        return walletHistoryDtoArrayList;
    }


//    public WalletHistory dtoToEntity(WalletHistoryDto dto) {
//        List<WalletMaster> updateWalletMasterTable = new ArrayList<>();
//        WalletHistory historyOfWallet = new WalletHistory();
//
//        historyOfWallet.setId(dto.getId());
//
//        historyOfWallet.setAmountPaid(dto.getAmountPaid());
//        historyOfWallet.setPaymentDate(dto.getPaymentDate());
//        historyOfWallet.setUsageStatus(dto.getUsageStatus());
//        historyOfWallet.setTeller(dto.getTeller());
//        historyOfWallet.setBankOrMerchantId(dto.getBankOrMerchantId());
//        historyOfWallet.setTellerPaymentDate(dto.getTellerPaymentDate());
//        historyOfWallet.setFeeDescription(dto.getFeeDescription());
//        historyOfWallet.setUnitAmount(dto.getUnitAmount());
//        historyOfWallet.setReferenceId(dto.getOrderId());
//        historyOfWallet.setTransactionId(dto.getTransactionId());
//        historyOfWallet.setChannelIdentifier(dto.getChannelIdentifier());
//
//        historyOfWallet.setPaymentSwitchSessionId(dto.getPaymentSwitchSessionId());
//        historyOfWallet.setPaymentNameFromBank(dto.getPaymentNameFromBank());
//        historyOfWallet.setSurchargeInKobo(dto.getSurchargeInKobo());
//        historyOfWallet.setChannel(dto.getChannel());
//        historyOfWallet.setEBillsPayTransactionCompleteDate(dto.getEBillsPayTransactionCompleteDate());
//        historyOfWallet.setNotificationCounter(dto.getNotificationCounter());
//        historyOfWallet.setReceivedPaymentNotification(dto.getReceivedPaymentNotification());
//        historyOfWallet.setDateCreated(dto.getDateCreated());
//        historyOfWallet.setLastUpdated(dto.getLastUpdated());
//        historyOfWallet.setIsDeleted(dto.getIsDeleted());
//        historyOfWallet.setRRR(dto.getRRR());
//        historyOfWallet.setPayerEmail(dto.getPayerEmail());
//        historyOfWallet.setPayerPhone(dto.getPayerPhone());
//
//
//        WalletMaster walletMaster = walletMasterRepository.findById(dto.getWalletMasterId()).orElse(null);
//        historyOfWallet.setWalletMaster(walletMaster);
//
//        historyOfWallet = walletHistoryRepository.save(historyOfWallet);
//
//        if(historyOfWallet.getPaymentStatus()== APPROVED) {
//
//            //set new balance into payment master
//            WalletMaster walletMasterTable = new WalletMaster();
//            WalletMasterDto walletMasterDto = new WalletMasterDto();
//
//            Double amountPaid = dto.getAmountPaid();
//            Double amountAvailable = walletMasterDto
//            Double previousTotalAmountRecovered = walletMasterDto.getTotalAmountRecovered();
//            Double newTotalAmountRecovered = previousTotalAmountRecovered + amountPaid;
//
//
//            Long walletMasterId = dto.getWalletMasterId();
//            walletMasterTable.setId(walletMasterId);
//            walletMasterTable.setBalance(walletMasterNewBalance);
//            walletMasterTable.setTotalAmountRecovered(newTotalAmountRecovered);
//
//            updatewalletMasterTable.add(walletMasterTable);
//
//            updatewalletMasterTable = walletMasterRepository.saveAll(updatewalletMasterTable);
//        }
//        return historyOfWallet;
//    }

}
