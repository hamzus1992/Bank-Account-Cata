package com.bank.account.kata.infra.repo;

import com.bank.account.kata.infra.domain.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationRepository extends JpaRepository<Operation,Long> {
}