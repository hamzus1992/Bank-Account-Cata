package com.bank.account.kata.infra.service;

import com.bank.account.kata.business.bank.account.model.BankAccountDto;
import com.bank.account.kata.infra.BankAccountKataInfraApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.test.StepVerifier;

import java.util.ArrayList;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = BankAccountKataInfraApplication.class)
public class BankAccountRepositoryTest {

    protected static final Long ID = 1L;
    protected static final String firstName = "HAMZA";
    protected static final String lastName = "RAKROUKI";
    protected static final Long balance = 100L;

    @Autowired
    protected BankAccountRepositoryAdapter bankAccountRepositoryAdapter;

    @Test
    public void should_save_bank_account() {
        BankAccountDto bankAccountDto = createBankAccount();
        bankAccountRepositoryAdapter.saveAccount(bankAccountDto)
                .as(StepVerifier::create)
                .expectNextMatches(bankAccountDto1 -> bankAccountDto1.getId().equals(bankAccountDto.getId()))
                .verifyComplete();
    }

    @Test
    public void should_find_by_id_account() {
        BankAccountDto bankAccountDto = createBankAccount();
        //verify the saving account
        bankAccountRepositoryAdapter.saveAccount(bankAccountDto)
                .as(StepVerifier::create)
                .expectNextMatches(bankAccountDto1 -> bankAccountDto1.getId().equals(bankAccountDto.getId()))
                .verifyComplete();

        //verify the find by id result
        bankAccountRepositoryAdapter.findById(ID)
                .as(StepVerifier::create)
                .expectNextMatches(bankAccountDto1 -> bankAccountDto1.getId().equals(bankAccountDto.getId()))
                .verifyComplete();

    }

    private BankAccountDto createBankAccount() {
        return BankAccountDto.builder().id(ID).balance(balance).firstName(firstName).lastName(lastName)
                .operations(new ArrayList<>())
                .build();
    }


}
