package com.bank.account.kata.business.bank.account.spi;

import com.bank.account.kata.business.bank.account.model.BankAccountDto;
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
