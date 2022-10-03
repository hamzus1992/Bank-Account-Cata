package com.bank.account.kata.business.bank.operation.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperationDto {

    private Long id;

    private Instant date;

    private OperationTypeDto type;

    private Long amount;

    private AccountDto account;

}
