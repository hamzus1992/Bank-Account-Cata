package com.bank.account.kata.infra;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@EnableTransactionManagement
@EnableJpaAuditing
@EnableWebFlux
public class BankAccountKataInfraApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankAccountKataInfraApplication.class, args);
    }

}
