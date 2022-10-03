package com.bank.account.kata.business.bank.operation.api;

import com.bank.account.kata.business.bank.operation.exception.AccountNotFoundException;
import com.bank.account.kata.business.bank.operation.model.BankAccountDto;
import reactor.core.publisher.Mono;

public interface OperationService {

    /**
     * debits the specified amount on the specified account
     * @param accountId the account identifier
     * @param amount the amount of the transaction
     * @throws AccountNotFoundException
     */
    Mono<BankAccountDto> doWithdrawal(long accountId, long amount) throws AccountNotFoundException;


    /**
     * deposit the specified amount into the specified account
     * @param accountId the account identifier
     * @param amount the amount of the transaction
     * @throws AccountNotFoundException
     */
    Mono<BankAccountDto> doDeposit(long accountId, long amount) throws AccountNotFoundException;

}
