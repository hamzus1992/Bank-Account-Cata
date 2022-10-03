package com.bank.account.kata.business.bank.account.business;

import com.bank.account.kata.business.bank.account.api.BankAccountService;
import com.bank.account.kata.business.bank.account.model.BankAccountDto;
import com.bank.account.kata.business.bank.account.spi.BankAccountRepository;
import com.bank.account.kata.business.bank.operation.exception.AccountNotFoundException;
import com.bank.account.kata.business.bank.operation.model.OperationDto;
import com.bank.account.kata.business.bank.operation.spi.OperationRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Slf4j
public class BankAccountServiceImpl implements BankAccountService {

    private final BankAccountRepository bankAccountRepository;
    private final OperationRepository operationRepository;

    @Override
    public Mono<BankAccountDto> printStatement(long accountId) {
        return bankAccountRepository.findById(accountId)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new AccountNotFoundException(String.valueOf(accountId)))));
    }

    @Override
    public Flux<OperationDto> listAllOperations(long accountId) {
        return operationRepository.findAllOperation(accountId);
    }
}
