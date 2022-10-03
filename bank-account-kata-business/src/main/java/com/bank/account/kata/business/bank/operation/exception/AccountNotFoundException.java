package com.bank.account.kata.business.bank.operation.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AccountNotFoundException extends Exception {

    private static final String MESSAGE = "Account with id '%s' not found";

    public AccountNotFoundException(String message) {
        super(String.format(MESSAGE,message));
        log.error(String.format(MESSAGE,message));
    }
}