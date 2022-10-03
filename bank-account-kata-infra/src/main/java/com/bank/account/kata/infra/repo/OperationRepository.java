package com.bank.account.kata.infra.repo;

import com.bank.account.kata.infra.domain.Operation;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface OperationRepository extends ReactiveCrudRepository<Operation, Long> {
}