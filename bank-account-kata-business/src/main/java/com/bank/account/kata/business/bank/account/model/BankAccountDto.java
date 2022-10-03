package com.bank.account.kata.business.bank.account.model;

import com.bank.account.kata.business.bank.operation.model.OperationDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankAccountDto {

    private Long id;

    private String firstName;

    private String lastName;

    private long balance;

    @JsonIgnore
    private List<OperationDto> operations = new ArrayList<>();

}
