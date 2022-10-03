package com.bank.account.kata.infra.service;

import com.bank.account.kata.business.bank.operation.api.OperationService;
import com.bank.account.kata.business.bank.operation.business.ServiceFactory;
import com.bank.account.kata.business.bank.operation.exception.AccountNotFoundException;
import com.bank.account.kata.business.bank.account.model.BankAccountDto;
import com.bank.account.kata.business.bank.account.spi.BankAccountRepository;
import com.bank.account.kata.business.bank.operation.spi.OperationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Service
@Slf4j
public class OperationServiceAdapter implements OperationService {

    private final OperationService operationService;

    @Autowired
    public OperationServiceAdapter(BankAccountRepository bankAccountRepository,
                                   OperationRepository operationRepository) {
        this.operationService = ServiceFactory.getInstance().create(operationRepository, bankAccountRepository);
    }


    @Override
    public Mono<BankAccountDto> doWithdrawal(long accountId, long amount) {
        return operationService.doWithdrawal(accountId, amount);
    }

    @Override
    public Mono<BankAccountDto> doDeposit(long accountId, long amount) {
        return operationService.doDeposit(accountId, amount);
    }
}
