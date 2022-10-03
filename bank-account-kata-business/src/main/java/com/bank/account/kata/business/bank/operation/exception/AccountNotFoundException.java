package com.bank.account.kata.business.bank.operation.exception;

public class AccountNotFoundException extends Exception {

    String message;

    public AccountNotFoundException(String message) {
        super(message);
        this.message = message;
    }
}