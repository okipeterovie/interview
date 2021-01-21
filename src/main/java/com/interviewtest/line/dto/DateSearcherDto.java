package com.interviewtest.line.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class DateSearcherDto {

    private LocalDate startDate;

    private LocalDate endDate;

    private String userProfileEmail;

}
