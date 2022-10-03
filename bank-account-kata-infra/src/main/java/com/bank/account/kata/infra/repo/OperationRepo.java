package com.bank.account.kata.infra.repo;

import com.bank.account.kata.infra.domain.Operation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface OperationRepo extends CrudRepository<Operation, Long> {

    @Query("select c from Operation c where c.id=:id")
    Operation findOperationById(Long id);
}