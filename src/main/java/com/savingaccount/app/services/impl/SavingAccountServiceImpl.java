package com.savingaccount.app.services.impl;

import com.savingaccount.app.models.dao.SavingAccountDao;
import com.savingaccount.app.models.documents.SavingAccount;
import com.savingaccount.app.models.dto.CreditCard;
import com.savingaccount.app.models.dto.Customer;
import com.savingaccount.app.services.SavingAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class SavingAccountServiceImpl implements SavingAccountService {
    private final WebClient webClient;
    private final ReactiveCircuitBreaker reactiveCircuitBreaker;

    @Value("${config.base.apigatewey}")
    private String url;

    public SavingAccountServiceImpl(ReactiveResilience4JCircuitBreakerFactory circuitBreakerFactory) {
        this.webClient = WebClient.builder().baseUrl(this.url).build();
        this.reactiveCircuitBreaker = circuitBreakerFactory.create("customerSaving");
    }

    @Autowired
    SavingAccountDao dao;

    @Override
    public Mono<Customer> findCustomerNumber(String number) {
        return reactiveCircuitBreaker.run(webClient.get().uri(this.url + "/customer/customer/documentNumber/{number}",number).accept(MediaType.APPLICATION_JSON).retrieve().bodyToMono(Customer.class),
                throwable -> {
                    return this.getDefaultCustomer();
                });
    }

    /**
     *
     * @param id del clinete para buscar cliente a traves del gatewey
     * @return con el CircuitBreaker si no lo encuentra ejecuta getDefaultCustomer
     */
    @Override
    public Mono<Customer> findCustomer(String id) {
        return reactiveCircuitBreaker.run(webClient.get().uri(this.url + "/customer/customer/find/{id}",id).accept(MediaType.APPLICATION_JSON).retrieve().bodyToMono(Customer.class),
                throwable -> {
                    return this.getDefaultCustomer();
                });
    }

    /**
     *
     * @return retorna una clse vacia si no responde la peticion con el webclient
     */
    public Mono<Customer> getDefaultCustomer() {
        Mono<Customer> customer = Mono.just(new Customer());
        return customer;
    }

    /**
     *
     * @param id
     * @return retorna la peticion echa al gatewey
     */
    @Override
    public Flux<CreditCard> findCreditCardByCustomer(String id) {
        return reactiveCircuitBreaker.run(webClient.get().uri(this.url + "/creditcard/creditcard/find/{id}",id).accept(MediaType.APPLICATION_JSON).retrieve().bodyToFlux(CreditCard.class),
                throwable -> {
                    return this.getDefaultCreditCard();
                });
    }

    /**
     *
     * @return una clase vacia si no responde la peticion
     */
    public Flux<CreditCard> getDefaultCreditCard() {
        Flux<CreditCard> creditCard = Flux.just(new CreditCard());
        return creditCard;
    }

    @Override
    public Mono<SavingAccount> create(SavingAccount savingAccount) {
        return dao.save(savingAccount);
    }

    @Override
    public Flux<SavingAccount> findAll() {
        return dao.findAll();
    }

    @Override
    public Mono<SavingAccount> findById(String id) {
        return dao.findById(id);
    }

    @Override
    public Mono<SavingAccount> update(SavingAccount savingAccount) {
        return dao.save(savingAccount);
    }

    @Override
    public Mono<Boolean> delete(String id) {
        return dao.findById(id)
                .flatMap(ds -> dao.delete(ds).then(Mono.just(Boolean.TRUE))
                ).defaultIfEmpty(Boolean.FALSE);
    }

    @Override
    public Mono<Long> findCustomerAccountBank(String id) {
        return dao.findByCustomerId(id).count();
    }

    @Override
    public Mono<Long> findCustomerAccountBankDocumentNumber(String number) {
        return dao.findByCustomerDocumentNumber(number).count();
    }

    @Override
    public Mono<SavingAccount> findByCardNumber(String number) {
        return dao.findByCardNumber(number);
    }

    @Override
    public Flux<SavingAccount> findCustomerById(String id) {
        return  dao.findByCustomerId(id);
    }
}
