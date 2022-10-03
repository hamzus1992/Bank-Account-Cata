package com.bank.account.kata.infra.repo;

import com.bank.account.kata.infra.domain.BankAccount;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface BankAccountRepo  extends CrudRepository<BankAccount,Long> {

    @Query("select c from BankAccount c where c.id=:id")
    BankAccount findBankAccountById(Long id);
}