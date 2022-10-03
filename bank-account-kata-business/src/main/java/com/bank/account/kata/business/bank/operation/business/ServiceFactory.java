package com.bank.account.kata.business.bank.operation.business;

import lombok.Getter;

public class ServiceFactory {

    @Getter
    private static final ServiceFactory Instance = new ServiceFactory();
}
