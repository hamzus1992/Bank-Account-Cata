package com.bank.account.kata.infra.controller;

import com.bank.account.kata.business.bank.account.model.BankAccountDto;
import com.bank.account.kata.business.bank.account.spi.BankAccountRepository;
import com.bank.account.kata.business.bank.operation.exception.AccountNotFoundException;
import com.bank.account.kata.business.bank.operation.model.OperationDto;
import com.bank.account.kata.business.bank.operation.model.OperationTypeDto;
import com.bank.account.kata.business.bank.operation.spi.OperationRepository;
import com.bank.account.kata.infra.BankAccountKataInfraApplication;
import com.bank.account.kata.infra.repo.BankAccountRepo;
import com.bank.account.kata.infra.repo.OperationRepo;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebFlux;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.event.annotation.BeforeTestMethod;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RunWith(SpringRunner.class)
@AutoConfigureWebFlux
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = BankAccountKataInfraApplication.class)
public class BankAccountControllerTest {

    protected static final Long ID = 1L;
    protected static final String ID_WRONG = "100";
    protected static final String firstName = "HAMZA";
    protected static final String lastName = "RAKROUKI";
    protected static final Long balance = 100L;


    protected static final String URI = "/api/v1/bankaccount";

    @Autowired
    protected BankAccountRepo bankAccountRepo;

    @Autowired
    protected BankAccountRepository bankAccountRepository;

    @Autowired
    protected OperationRepo operationRepo;

    @Autowired
    protected OperationRepository operationRepository;

    @Autowired
    protected WebTestClient webTestClient;


    @BeforeTestMethod
    public void setUpMethod() {
        operationRepo.deleteAll();
        bankAccountRepo.deleteAll();
    }

    @Test
    public void should_print_account_statement() {
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

        assert result != null;
        Assertions.assertEquals(bankAccountDto.getBalance(), result.getBalance());


    }

    @Test
    public void should_print_account_statement_exception_not_found() {

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
    public void should_list_all_operations() {
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
            Assertions.assertEquals(operationDtoList.get(i).getAmount(), result.get(i).getAmount());
            Assertions.assertEquals(operationDtoList.get(i).getType(), result.get(i).getType());
        }
    }

    @Test
    public void should_deposit_in_bank_account() {
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
    }

    @Test
    public void should_withdraw_in_bank_account() {
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
        Assertions.assertEquals(Objects.requireNonNull(result).getBalance(), bankAccountDto.getBalance() - 100L);
    }

    protected BankAccountDto saveBankAccount() {
        BankAccountDto bankAccountDto = BankAccountDto.builder().id(ID).balance(balance).firstName(firstName).lastName(lastName)
                .operations(new ArrayList<>())
                .build();
        //save in database
        return bankAccountRepository.saveAccount(bankAccountDto).block();
    }
}
