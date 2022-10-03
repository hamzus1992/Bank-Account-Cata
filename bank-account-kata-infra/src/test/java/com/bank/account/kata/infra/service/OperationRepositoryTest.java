package com.bank.account.kata.infra.service;


import com.bank.account.kata.business.bank.account.model.BankAccountDto;
import com.bank.account.kata.business.bank.account.spi.BankAccountRepository;
import com.bank.account.kata.business.bank.operation.model.OperationDto;
import com.bank.account.kata.business.bank.operation.model.OperationTypeDto;
import com.bank.account.kata.business.bank.operation.spi.OperationRepository;
import com.bank.account.kata.infra.BankAccountKataInfraApplication;
import com.bank.account.kata.infra.repo.BankAccountRepo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.test.StepVerifier;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = BankAccountKataInfraApplication.class)
public class OperationRepositoryTest {

    protected static final Long ID = 1L;
    protected static final String firstName = "HAMZA";
    protected static final String lastName = "RAKROUKI";
    protected static final Long balance = 100L;

    @Autowired
    private OperationRepositoryAdapter operationRepositoryAdapter;

    @Autowired
    protected BankAccountRepo bankAccountRepo;

    @Autowired
    protected BankAccountRepository bankAccountRepository;

    @Autowired
    protected OperationRepository operationRepository;

    @Test
    public void should_save_the_operation() {
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
    public void should_list_all_the_operation_from_database() {
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

    protected BankAccountDto saveBankAccount() {
        BankAccountDto bankAccountDto = BankAccountDto.builder().id(ID).balance(balance).firstName(firstName).lastName(lastName)
                .operations(new ArrayList<>())
                .build();
        //save in database
        return bankAccountRepository.saveAccount(bankAccountDto).block();
    }
}
