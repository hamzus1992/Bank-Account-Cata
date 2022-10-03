package com.bank.account.kata.infra.domain;

public enum OperationType {

    DEPOSIT("deposit"),
    WITHDRAWAL("withdrawal");

    String operation;

    OperationType(String operation) {
        this.operation = operation;
    }
}