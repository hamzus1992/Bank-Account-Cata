package com.bank.account.kata.business.bank.operation.spi;

import com.bank.account.kata.business.bank.operation.model.AccountDto;
import reactor.core.publisher.Mono;

public interface BankAccountRepository {

    Mono<AccountDto> findById(Long id);

    Mono<AccountDto> saveAccount(AccountDto accountDto);

}
