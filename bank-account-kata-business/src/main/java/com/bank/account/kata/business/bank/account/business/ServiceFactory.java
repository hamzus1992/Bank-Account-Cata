package com.bank.account.kata.business.bank.account.business;

import com.bank.account.kata.business.bank.account.api.BankAccountService;
import com.bank.account.kata.business.bank.account.spi.BankAccountRepository;
import com.bank.account.kata.business.bank.operation.spi.OperationRepository;
import lombok.Getter;

public class ServiceFactory {
    @Getter
    private static final ServiceFactory Instance = new ServiceFactory();

    /**
     * Create bank account Service.
     *
     * @param bankAccountRepository the bank account repository
     * @return the bank account service
     */
    public BankAccountService create(BankAccountRepository bankAccountRepository, OperationRepository operationRepository) {
        return new BankAccountServiceImpl(bankAccountRepository, operationRepository);
    }
}
