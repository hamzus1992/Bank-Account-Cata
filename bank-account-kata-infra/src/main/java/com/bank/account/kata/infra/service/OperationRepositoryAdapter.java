package com.bank.account.kata.infra.service;

import com.bank.account.kata.business.bank.operation.model.OperationDto;
import com.bank.account.kata.business.bank.operation.spi.OperationRepository;
import com.bank.account.kata.infra.domain.Operation;
import com.bank.account.kata.infra.mapper.GenericObjectMapper;
import com.bank.account.kata.infra.mapper.impl.GenericObjectMapperImpl;
import com.bank.account.kata.infra.repo.BankAccountRepo;
import com.bank.account.kata.infra.repo.OperationRepo;
import com.github.dozermapper.core.Mapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class OperationRepositoryAdapter implements OperationRepository {

    private final OperationRepo operationRepository;
    private final BankAccountRepo bankAccountRepo;

    private final GenericObjectMapper<Operation, OperationDto> inDataMapper;
    private final GenericObjectMapper<OperationDto, Operation> outDataMapper;

    @Autowired
    public OperationRepositoryAdapter(OperationRepo operationRepository,
                                      BankAccountRepo bankAccountRepo,
                                      Mapper operationMapper) {
        this.operationRepository = operationRepository;
        this.bankAccountRepo = bankAccountRepo;
        this.inDataMapper = new GenericObjectMapperImpl<>(operationMapper, OperationDto.class);
        this.outDataMapper = new GenericObjectMapperImpl<>(operationMapper, Operation.class);
    }


    @Override
    public Mono<OperationDto> findOperationById(Long id) {
        return Mono.just(operationRepository.findOperationById(id))
                .map(inDataMapper::convert);
    }

    @Override
    public Mono<OperationDto> saveOperation(OperationDto operationDto) {
        return Mono.just(operationRepository.save(outDataMapper.convert(operationDto)))
                .map(inDataMapper::convert);
    }

    @Override
    public Flux<OperationDto> findAllOperation(Long idAccount) {
        return Mono.just(bankAccountRepo.findBankAccountById(idAccount))
                .flatMapMany(bankAccount -> Flux.fromStream(bankAccount
                        .getOperations()
                        .stream()
                        .map(inDataMapper::convert)));
    }
}
