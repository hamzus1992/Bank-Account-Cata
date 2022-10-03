package com.bank.account.kata.infra.config;

import com.bank.account.kata.business.bank.account.model.BankAccountDto;
import com.bank.account.kata.business.bank.operation.model.OperationDto;
import com.bank.account.kata.infra.domain.BankAccount;
import com.bank.account.kata.infra.domain.Operation;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import com.github.dozermapper.core.loader.api.BeanMappingBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * A dozer mapping configuration class.
 */
@Configuration
public class DozerMapperConfig {

    /**
     * Contract in mapper dozer bean mapper.
     *
     * @return the dozer bean mapper
     */
    @Bean
    public Mapper mapper() {

        // contract & item builder to define mapping
        BeanMappingBuilder builder = new BeanMappingBuilder() {
            @Override
            protected void configure() {
                mapping(BankAccount.class, BankAccountDto.class);
                mapping(Operation.class, OperationDto.class);
            }
        };
        return DozerBeanMapperBuilder.create().withMappingBuilder(builder).build();
    }
}