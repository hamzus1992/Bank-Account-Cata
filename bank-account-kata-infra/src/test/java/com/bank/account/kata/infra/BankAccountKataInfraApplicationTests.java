package com.bank.account.kata.infra;

import com.bank.account.kata.business.bank.account.model.BankAccountDto;
import com.bank.account.kata.business.bank.account.spi.BankAccountRepository;
import com.bank.account.kata.business.bank.operation.spi.OperationRepository;
import com.bank.account.kata.infra.repo.BankAccountRepo;
import com.bank.account.kata.infra.repo.OperationRepo;
import com.bank.account.kata.infra.service.BankAccountRepositoryAdapter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebFlux;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.ArrayList;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,classes = BankAccountKataInfraApplication.class)
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@AutoConfigureWebFlux
public class BankAccountKataInfraApplicationTests {

    //Context
    protected static final Long ID = 1L;
    protected static final String ID_WRONG = "100";
    protected static final String firstName = "HAMZA";
    protected static final String lastName = "RAKROUKI";
    protected static final Long balance = 100L;

    protected static final String URI = "/api/v1/bankaccount";

    @Autowired
    protected BankAccountRepositoryAdapter bankAccountRepositoryAdapter;

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

    @Test
    void contextLoads() {
    }


    @AfterEach
    public void cleanUp() {
        bankAccountRepo.deleteAll();
        operationRepo.deleteAll();
    }

    protected BankAccountDto saveBankAccount() {
        BankAccountDto bankAccountDto = BankAccountDto.builder().id(ID).balance(balance).firstName(firstName).lastName(lastName)
                .operations(new ArrayList<>())
                .build();
        //save in database
        return bankAccountRepository.saveAccount(bankAccountDto).block();
    }

}
