package com.bank.account.kata.infra.service;

import com.bank.account.kata.business.bank.operation.model.BankAccountDto;
import com.bank.account.kata.business.bank.operation.spi.BankAccountRepository;
import com.bank.account.kata.infra.domain.BankAccount;
import com.bank.account.kata.infra.mapper.GenericObjectMapper;
import com.bank.account.kata.infra.mapper.impl.GenericObjectMapperImpl;
import com.bank.account.kata.infra.repo.BankAccountRepo;
import com.github.dozermapper.core.Mapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class BankAccountRepositoryAdapter implements BankAccountRepository {

    private final BankAccountRepo bankAccountRepository;

    private final GenericObjectMapper<BankAccount, BankAccountDto> inDataMapper;
    private final GenericObjectMapper<BankAccountDto, BankAccount> outDataMapper;

    @Autowired
    public BankAccountRepositoryAdapter(BankAccountRepo bankAccountRepository,
                                        Mapper bankAccountMapper) {
        this.bankAccountRepository = bankAccountRepository;
        this.inDataMapper = new GenericObjectMapperImpl<>(bankAccountMapper, BankAccountDto.class);
        this.outDataMapper = new GenericObjectMapperImpl<>(bankAccountMapper, BankAccount.class);
    }


    @Override
    public Mono<BankAccountDto> findById(Long id) {
        return bankAccountRepository.findById(id)
                .map(inDataMapper::convert);
    }

    @Override
    public Mono<BankAccountDto> saveAccount(BankAccountDto accountDto) {
        return bankAccountRepository.save(outDataMapper.convert(accountDto))
                .map(inDataMapper::convert);
    }
}
