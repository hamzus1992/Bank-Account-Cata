package com.bank.account.kata.infra.service;


import com.bank.account.kata.business.bank.account.model.BankAccountDto;
import com.bank.account.kata.business.bank.operation.model.OperationDto;
import com.bank.account.kata.business.bank.operation.model.OperationTypeDto;
import com.bank.account.kata.infra.BankAccountKataInfraApplicationTests;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.test.StepVerifier;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class OperationRepositoryTest  extends BankAccountKataInfraApplicationTests {

    @Autowired
    private OperationRepositoryAdapter operationRepositoryAdapter;



    @Test
    void should_save_the_operation() {
        operationRepository.saveOperation(OperationDto.builder()
                .id(1L)
                .amount(100L)
                .date(Instant.now())
                .type(OperationTypeDto.DEPOSIT).build())
                .as(StepVerifier::create)
                .expectNextMatches(Objects::nonNull)
                .verifyComplete();
    }

    @Test
    void should_list_all_the_operation_from_database() {
        List<OperationDto> operationDtoList = new ArrayList<>();

        //create bank account
        BankAccountDto bankAccountDto = saveBankAccount();

        //add some operations and save to database
        operationRepository.saveOperation(OperationDto.builder()
                .amount(100L)
                .date(Instant.now())
                .account(bankAccountDto)
                .type(OperationTypeDto.DEPOSIT).build()).block();

        operationRepository.saveOperation(OperationDto.builder()
                .amount(50L)
                .date(Instant.now())
                .account(bankAccountDto)
                .type(OperationTypeDto.WITHDRAWAL).build()).block();

        operationRepositoryAdapter.findAllOperation(ID)
                .as(StepVerifier::create)
                .assertNext(op1 -> op1.getType().equals(OperationTypeDto.DEPOSIT))
                .assertNext(op2 -> op2.getType().equals(OperationTypeDto.WITHDRAWAL))
                .verifyComplete();


    }
}
