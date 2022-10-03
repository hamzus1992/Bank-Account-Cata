package com.bank.account.kata.business.bank.operation.business;

import com.bank.account.kata.business.bank.operation.api.OperationService;
import com.bank.account.kata.business.bank.account.spi.BankAccountRepository;
import com.bank.account.kata.business.bank.operation.spi.OperationRepository;
import lombok.Getter;

public class ServiceFactory {

    @Getter
    private static final ServiceFactory Instance = new ServiceFactory();

    /**
     * Create operation Service.
     *
     * @param operationRepository the operation repository
     * @param bankAccountRepository the bank account repository
     * @return the operation service
     */
    public OperationService create(OperationRepository operationRepository,
                                   BankAccountRepository bankAccountRepository) {
        return new OperationServiceImpl(operationRepository, bankAccountRepository);
    }
}
