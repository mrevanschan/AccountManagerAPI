package com.acmebank.accountmanager.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferRequest {
    Long toAccount;
    BigDecimal amount;
}
