package com.bank.account.kata.infra.repo;

import com.bank.account.kata.infra.domain.BankAccount;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface BankAccountRepository  extends ReactiveCrudRepository<BankAccount,Long> {
}