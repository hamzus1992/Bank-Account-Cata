package com.bank.account.kata.business.bank.operation.spi;

import com.bank.account.kata.business.bank.operation.exception.AccountNotFoundException;
import com.bank.account.kata.business.bank.operation.model.BankAccountDto;
import reactor.core.publisher.Mono;

public interface BankAccountRepository {

    /**
     * find account by id
     * @param id the account identifier
     * @return bank account
     */
    Mono<BankAccountDto> findById(Long id);

    /**
     * save account
     * @param accountDto the account
     * @return bank account
     */
    Mono<BankAccountDto> saveAccount(BankAccountDto accountDto);

}
