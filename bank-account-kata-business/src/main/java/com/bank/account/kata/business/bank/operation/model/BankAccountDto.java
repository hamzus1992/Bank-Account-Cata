package com.bank.account.kata.business.bank.operation.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankAccountDto {

    private Long id;

    private long balance;

    private List<OperationDto> latestOperations;

}
