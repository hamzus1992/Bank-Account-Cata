package com.bank.account.kata.business.bank.operation.business;

import com.bank.account.kata.business.bank.operation.api.OperationService;
import com.bank.account.kata.business.bank.operation.exception.AccountNotFoundException;
import com.bank.account.kata.business.bank.operation.model.AccountDto;
import com.bank.account.kata.business.bank.operation.model.OperationDto;
import com.bank.account.kata.business.bank.operation.model.OperationTypeDto;
import com.bank.account.kata.business.bank.operation.spi.BankAccountRepository;
import com.bank.account.kata.business.bank.operation.spi.OperationRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import java.time.Instant;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Slf4j
public class OperationServiceImpl implements OperationService {


    private final OperationRepository operationRepository;

    private final BankAccountRepository bankAccountRepository;

    @Override
    public Mono<AccountDto> doWithdrawal(long accountId, long amount) {
        return bankAccountRepository.findById(accountId)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new AccountNotFoundException(String.valueOf(accountId)))))
                .flatMap(account -> createOperation(account, amount, OperationTypeDto.WITHDRAWAL))
                .map(operationDto -> createAccount(operationDto, amount, OperationTypeDto.WITHDRAWAL))
                .flatMap(bankAccountRepository::saveAccount);
    }

    @Override
    public Mono<AccountDto> doDeposit(long accountId, long amount) {
        return bankAccountRepository.findById(accountId)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new AccountNotFoundException(String.valueOf(accountId)))))
                .flatMap(account -> createOperation(account, amount, OperationTypeDto.DEPOSIT))
                .map(operationDto -> createAccount(operationDto, amount, OperationTypeDto.DEPOSIT))
                .flatMap(bankAccountRepository::saveAccount);
    }

    private Mono<OperationDto> createOperation(AccountDto accountDto, long amount, OperationTypeDto operationTypeDto) {
        return operationRepository.saveOperation(OperationDto.builder()
                .date(Instant.now())
                .account(accountDto)
                .type(operationTypeDto)
                .amount(amount)
                .build());
    }


    private AccountDto createAccount(OperationDto operationDto, long amount, OperationTypeDto operationTypeDto) {
        operationDto.getAccount().getLatestOperations().add(operationDto);
        operationDto.getAccount().setBalance(operationDto.getAccount().getBalance() + ((operationTypeDto.equals(OperationTypeDto.DEPOSIT) ? 1 : -1) * amount));
        return operationDto.getAccount();
    }
}
