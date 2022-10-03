package com.bank.account.kata.business.bank.operation.model;

public enum OperationTypeDto {

    DEPOSIT("deposit"),
    WITHDRAWAL("withdrawal");

    String operation;

    OperationTypeDto(String operation) {
        this.operation = operation;
    }
}
