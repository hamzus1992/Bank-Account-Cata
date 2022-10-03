package com.bank.account.kata.infra.controller;

import com.bank.account.kata.business.bank.account.model.BankAccountDto;
import com.bank.account.kata.business.bank.operation.exception.AccountNotFoundException;
import com.bank.account.kata.business.bank.operation.model.OperationDto;
import com.bank.account.kata.business.bank.operation.model.OperationTypeDto;
import com.bank.account.kata.infra.BankAccountKataInfraApplicationTests;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
class BankAccountControllerTest extends BankAccountKataInfraApplicationTests {


    @Test
    void should_print_account_statement() {
        //create bank account
        BankAccountDto bankAccountDto = saveBankAccount();

        //test web response
        BankAccountDto result = webTestClient.get().uri(URI + "/" + ID)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(BankAccountDto.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertEquals(bankAccountDto, result);


    }

    @Test
    void should_print_account_statement_exception_not_found() {

        //test web response
        Objects.requireNonNull(webTestClient.get().uri(URI + "/" + ID_WRONG)
                .exchange()
                .expectStatus()
                .is5xxServerError()
                .expectBody(AccountNotFoundException.class)
                .returnResult()
                .getResponseBody());

    }

    @Test
    void should_list_all_operations() {
        List<OperationDto> operationDtoList = new ArrayList<>();

        //create bank account
        BankAccountDto bankAccountDto = saveBankAccount();
        //add some operations and save to database
        operationDtoList.add(operationRepository.saveOperation(OperationDto.builder()
                .amount(100L)
                .date(Instant.now())
                .account(bankAccountDto)
                .type(OperationTypeDto.DEPOSIT).build()).block());

        operationDtoList.add(operationRepository.saveOperation(OperationDto.builder()
                .amount(100L)
                .date(Instant.now())
                .account(bankAccountDto)
                .type(OperationTypeDto.WITHDRAWAL).build()).block());


        List<OperationDto> result = webTestClient.get()
                .uri(URI + "/" + ID + "/history")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(OperationDto.class)
                .returnResult()
                .getResponseBody();

        assert result != null;
        Assertions.assertEquals(operationDtoList.size(), result.size());
        for (int i = 0; i < operationDtoList.size(); i++) {
            Assertions.assertEquals(operationDtoList.get(i).getId(), result.get(i).getId());
            Assertions.assertEquals(operationDtoList.get(i).getAmount(), result.get(i).getAmount());
            Assertions.assertEquals(operationDtoList.get(i).getType(), result.get(i).getType());
        }
    }

    @Test
    void should_deposit_in_bank_account() {
        //create bank account
        BankAccountDto bankAccountDto = saveBankAccount();

        BankAccountDto result = webTestClient.put()
                .uri(uriBuilder -> uriBuilder.path(URI + "/" + ID + "/deposit")
                        .queryParam("amount", 100L).build())
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(BankAccountDto.class)
                .returnResult()
                .getResponseBody();
        Assertions.assertEquals(Objects.requireNonNull(result).getBalance(), bankAccountDto.getBalance() + 100L);
        Assertions.assertEquals(1, result.getOperations().size());
    }

    @Test
    void should_withdraw_in_bank_account() {
        //create bank account
        BankAccountDto bankAccountDto = saveBankAccount();

        BankAccountDto result = webTestClient.put()
                .uri(uriBuilder -> uriBuilder.path(URI + "/" + ID + "/withdrawal")
                        .queryParam("amount", 100L).build())
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(BankAccountDto.class)
                .returnResult()
                .getResponseBody();
        Assertions.assertEquals(Objects.requireNonNull(result).getBalance(), bankAccountDto.getBalance() + 100L);
        Assertions.assertEquals(1, result.getOperations().size());
    }




}
