package com.bank.account.kata.business.bank.operation.spi;

import com.bank.account.kata.business.bank.operation.model.OperationDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface OperationRepository {

    Mono<OperationDto> findOperationById(Long id);

    Mono<OperationDto> saveOperation(OperationDto operationDto);

    Flux<OperationDto> findAllOperation();

}
