package com.bank.account.kata.infra.controller;

import com.bank.account.kata.business.bank.account.model.BankAccountDto;
import com.bank.account.kata.business.bank.operation.model.OperationDto;
import com.bank.account.kata.infra.service.BankAccountServiceAdapter;
import com.bank.account.kata.infra.service.OperationServiceAdapter;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/bankaccount")
public class BankAccountController {

    private final OperationServiceAdapter operationServiceAdapter;
    private final BankAccountServiceAdapter bankAccountServiceAdapterAdapter;

    @Autowired
    public BankAccountController(OperationServiceAdapter operationServiceAdapter,
                                 BankAccountServiceAdapter bankAccountServiceAdapterAdapter) {
        this.operationServiceAdapter = operationServiceAdapter;
        this.bankAccountServiceAdapterAdapter = bankAccountServiceAdapterAdapter;
    }

    @ApiOperation(value = "printAccountState", notes = "return given account state and recent operations")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = BankAccountDto.class),
            @ApiResponse(code = 404, message = "Bad request"),
    })
    @GetMapping("{accountId}")
    public Mono<BankAccountDto> printAccountState(@PathVariable long accountId) {
        return bankAccountServiceAdapterAdapter.printStatement(accountId);
    }

    @ApiOperation(value = "showOperationsList", notes = "lists all given account operations")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", responseContainer = "list", response = OperationDto.class),
            @ApiResponse(code = 404, message = "Bad request"),
    })
    @GetMapping("{accountId}/history")
    public Flux<OperationDto> showOperationsList(@PathVariable long accountId) {
        return bankAccountServiceAdapterAdapter.listAllOperations(accountId);
    }


    @ApiOperation(value = "deposit", notes = "perfom a deposit on the given account")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = BankAccountDto.class),
            @ApiResponse(code = 404, message = "Bad request"),
    })
    @PutMapping(value = "{accountId}/deposit")
    public Mono<BankAccountDto> deposit(@PathVariable long accountId,
                                        @RequestParam Long amount) {
        return operationServiceAdapter.doDeposit(accountId, amount);
    }


    @ApiOperation(value = "withdrawall", notes = "perfom a withdrawal on the given account")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = BankAccountDto.class),
            @ApiResponse(code = 404, message = "Bad request"),
    })
    @PutMapping(value = "{accountId}/withdrawal")
    public Mono<BankAccountDto> withdrawal(@PathVariable long accountId,
                                           @RequestParam Long amount) {
        return operationServiceAdapter.doWithdrawal(accountId, amount);
    }


}
