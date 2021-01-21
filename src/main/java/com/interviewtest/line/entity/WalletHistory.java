package com.interviewtest.line.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.interviewtest.line.enumeration.PaymentStatus;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
@Table(name = "WALLET_HISTORY")
public class WalletHistory implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "PAYMENT_STATUS")
    private PaymentStatus paymentStatus;

    @Column(name = "AMOUNT_PAID")
    private Double amountPaid;

    @CreationTimestamp
    @Column(name = "PAYMENT_DATE")
    private Date paymentDate;

    @Column(name = "USAGE_STATUS")
    private String usageStatus;

    @Column(name = "TELLER")
    private String teller;

    @Column(name = "BANK_OR_MERCHANT_ID")
    private String bankOrMerchantId;

    @Column(name = "TELLER_PAYMENT_DATE")
    private Date tellerPaymentDate;

    @Column(name = "FEE_DESCRIPTION")
    private String feeDescription;

    @Column(name = "UNIT_AMOUNT")
    private Double unitAmount;

    @Column(name = "REFERENCE_ID")
    private String referenceId;

    @Column(name="transaction_id")
    private String transactionId;

    @Column(name = "CHANNEL_IDENTIFIER")
    private String channelIdentifier;

    @Column(name = "PAYMENT_SWITCH_SESSION_ID")
    private String paymentSwitchSessionId;

    @Column(name="RRR")
    private String RRR;

    @Column(name = "PAYEE_NAME_FROM_BANK")
    private String paymentNameFromBank;

    @Column(name = "SURCHARGE_IN_KOBO")
    private Long surchargeInKobo;

    @Column(name = "CHANNEL")
    private String channel;

    @Column(name = "EBILLS_PAY_TRANSACTION_COMPLETE_DATE")
    private LocalDate eBillsPayTransactionCompleteDate;

    @Column(name = "NOTIFICATION_COUNTER")
    private Integer notificationCounter;

    @Column(name = "RECEIVED_PAYMENT_NOTIFICATION")
    private Boolean receivedPaymentNotification;

    @CreationTimestamp
    @Column(name = "DATE_CREATED")
    private Date dateCreated;

    @UpdateTimestamp
    @Column(name = "LAST_UPDATED")
    private LocalDateTime lastUpdated;

    @Column(name = "IS_DELETED", nullable = true)
    private Boolean isDeleted = Boolean.FALSE;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "WALLET_MASTER_ID", referencedColumnName = "id")
    private WalletMaster walletMaster;

    @Column(name="SERVICE_TYPE_ID")
    private String serviceTypeId;

    @Column(name = "ORDER_ID")
    private String orderId;

    @Column(name= "PAYER_EMAIL")
    private String payerEmail;

    @Column(name= "PAYER_PHONE")
    private String payerPhone;


}
