package com.bank.account.kata.infra.service;

import com.bank.account.kata.business.bank.account.model.BankAccountDto;
import com.bank.account.kata.infra.BankAccountKataInfraApplicationTests;
import com.bank.account.kata.infra.repo.BankAccountRepo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.test.StepVerifier;

import java.util.ArrayList;

@Slf4j
class BankAccountRepositoryTest extends BankAccountKataInfraApplicationTests {

    @Test
    void should_save_bank_account() {
        BankAccountDto bankAccountDto = createBankAccount();
        bankAccountRepositoryAdapter.saveAccount(bankAccountDto)
                .as(StepVerifier::create)
                .expectNextMatches(bankAccountDto1 -> bankAccountDto1.getId().equals(bankAccountDto.getId()))
                .verifyComplete();
    }

    @Test
    void should_find_by_id_account() {
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
