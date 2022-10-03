package com.bank.account.kata.business.bank.account.api;

import com.bank.account.kata.business.bank.account.model.BankAccountDto;
import com.bank.account.kata.business.bank.operation.model.OperationDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BankAccountService {

    /**
     *
     * @param accountId account identifier
     * @return  the account state including latest operations
     */
    Mono<BankAccountDto> printStatement(long accountId);

    /**
     *
     * @param accountId account identifier
     * @return all operations on a given account
     */
    Flux<OperationDto> listAllOperations(long accountId);
}
