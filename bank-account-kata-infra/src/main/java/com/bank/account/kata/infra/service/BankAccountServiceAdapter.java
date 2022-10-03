package com.bank.account.kata.infra.service;

import com.bank.account.kata.business.bank.account.api.BankAccountService;
import com.bank.account.kata.business.bank.account.model.BankAccountDto;
import com.bank.account.kata.business.bank.account.spi.BankAccountRepository;
import com.bank.account.kata.business.bank.account.business.ServiceFactory;
import com.bank.account.kata.business.bank.operation.model.OperationDto;
import com.bank.account.kata.business.bank.operation.spi.OperationRepository;
import com.bank.account.kata.infra.repo.BankAccountRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class BankAccountServiceAdapter implements BankAccountService {

    private final BankAccountService bankAccountService;

    public BankAccountServiceAdapter(BankAccountRepository bankAccountRepository,
                                     OperationRepository operationRepository) {
        this.bankAccountService =  ServiceFactory.getInstance().create(bankAccountRepository, operationRepository);
    }

    @Override
    public Mono<BankAccountDto> printStatement(long accountId) {
        return bankAccountService.printStatement(accountId);
    }

    @Override
    public Flux<OperationDto> listAllOperations(long accountId) {
        return bankAccountService.listAllOperations(accountId);
    }
}
