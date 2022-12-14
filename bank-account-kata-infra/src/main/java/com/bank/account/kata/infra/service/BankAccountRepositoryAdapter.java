package com.bank.account.kata.infra.service;

import com.bank.account.kata.business.bank.account.model.BankAccountDto;
import com.bank.account.kata.business.bank.account.spi.BankAccountRepository;
import com.bank.account.kata.business.bank.operation.exception.AccountNotFoundException;
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

    private final BankAccountRepo bankAccountRepo;

    private final GenericObjectMapper<BankAccount, BankAccountDto> inDataMapper;
    private final GenericObjectMapper<BankAccountDto, BankAccount> outDataMapper;

    @Autowired
    public BankAccountRepositoryAdapter(BankAccountRepo bankAccountRepo,
                                        Mapper bankAccountMapper) {
        this.bankAccountRepo = bankAccountRepo;
        this.inDataMapper = new GenericObjectMapperImpl<>(bankAccountMapper, BankAccountDto.class);
        this.outDataMapper = new GenericObjectMapperImpl<>(bankAccountMapper, BankAccount.class);
    }


    @Override
    public Mono<BankAccountDto> findById(Long id) {
        BankAccount bankAccount = bankAccountRepo.findBankAccountById(id);
        if(bankAccount != null) {
            return Mono.just(bankAccount)
                    .switchIfEmpty(Mono.defer(() -> Mono.error(new AccountNotFoundException(String.valueOf(id)))))
                    .map(inDataMapper::convert);
        } else {
            return Mono.defer(() -> Mono.error(new AccountNotFoundException(String.valueOf(id))));
        }
    }

    @Override
    public Mono<BankAccountDto> saveAccount(BankAccountDto accountDto) {
        return Mono.just(bankAccountRepo.save(outDataMapper.convert(accountDto)))
                .map(inDataMapper::convert);
    }
}
