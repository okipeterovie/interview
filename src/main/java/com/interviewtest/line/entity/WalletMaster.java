/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.interviewtest.line.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author Oki-Peter
 */


@Data
@Entity
@Table(name="wallet_master")
public class WalletMaster implements Serializable  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")  
    private Long id;

    @CreationTimestamp
    @Column(name="time_created")
    private LocalDateTime timeCreated;

    @UpdateTimestamp
    @Column(name="last_updated")
    private LocalDateTime lastUpdated;

    @Column(name="total_amount_available")
    private Double totalAmountAvailable;

    @ManyToOne
    @JoinColumn(name="outlet_id", referencedColumnName="id")
    private Outlets outlets;

    @ManyToOne
    @JoinColumn(name="user_profile_id", referencedColumnName="id")
    private UserProfile userProfile;



}
