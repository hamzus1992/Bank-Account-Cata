package com.bank.account.kata.infra.repo;

import com.bank.account.kata.infra.domain.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository  extends JpaRepository<BankAccount,Long>{
}