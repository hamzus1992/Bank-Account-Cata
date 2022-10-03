package com.bank.account.kata.business.bank.operation.spi;

import com.bank.account.kata.business.bank.operation.model.OperationDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface OperationRepository {

    /**
     * save operation
     * @param operationDto the operation
     * @return operation
     */
    Mono<OperationDto> saveOperation(OperationDto operationDto);

    /**
     * find all operations
     * @param idAccount
     * @return list of operation
     */
    Flux<OperationDto> findAllOperation(Long idAccount);

}
