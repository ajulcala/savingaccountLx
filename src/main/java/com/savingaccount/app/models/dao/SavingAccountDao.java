package com.savingaccount.app.models.dao;

import com.savingaccount.app.models.documents.SavingAccount;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SavingAccountDao extends ReactiveMongoRepository<SavingAccount, String> {
    Flux<SavingAccount> findByCustomerId(String id);
    Flux<SavingAccount> findByCustomerDocumentNumber(String documentNumber);
    Mono<SavingAccount> findByCardNumber(String number);
}
