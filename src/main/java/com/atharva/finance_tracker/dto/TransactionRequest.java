package com.atharva.finance_tracker.dto;

import com.atharva.finance_tracker.entity.TransactionType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class TransactionRequest {

    private BigDecimal amount;

    private String category;

    private TransactionType type;

    private String description;

    private LocalDate transactionDate;
}