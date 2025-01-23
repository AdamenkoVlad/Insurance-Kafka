package com.insurance.claimservice.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class PolicyDTO {

    private Long id;

    private String policyNumber;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal premiumAmount;
    private Long customerId;

}
