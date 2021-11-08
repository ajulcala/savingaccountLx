package com.savingaccount.app.services;

import com.savingaccount.app.models.documents.SavingAccount;
import com.savingaccount.app.models.dto.CreditCard;
import com.savingaccount.app.models.dto.Customer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SavingAccountService {
    Mono<SavingAccount> create(SavingAccount savingAccount);
    Flux<SavingAccount> findAll();
    Mono<Customer>findCustomerNumber(String number);
    Mono<Customer>findCustomer(String id);
    Mono<SavingAccount> findById(String id);
    Mono<SavingAccount> update(SavingAccount savingAccount);
    Mono<Boolean> delete(String id);
    Mono<Long>findCustomerAccountBank(String id);
    Mono<Long>findCustomerAccountBankDocumentNumber(String number);
    Flux<CreditCard> findCreditCardByCustomer(String id);
    Mono<SavingAccount> findByCardNumber(String number);
    Flux<SavingAccount> findCustomerById(String id);
}
