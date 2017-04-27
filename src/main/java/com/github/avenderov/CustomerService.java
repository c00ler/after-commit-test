package com.github.avenderov;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.Uninterruptibles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class CustomerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);

    private final CustomerRepository repository;

    private final AfterCommitExecutor afterCommitExecutor;

    public CustomerService(final CustomerRepository repository, final AfterCommitExecutor afterCommitExecutor) {
        this.repository = repository;
        this.afterCommitExecutor = afterCommitExecutor;
    }

    @Transactional
    public Collection<Customer> findAll() {
        final List<Customer> customers = Lists.newArrayList(repository.findAll());

        afterCommitExecutor.onSuccess(() -> {
            LOGGER.info("Sleeping for 5 seconds...");

            Uninterruptibles.sleepUninterruptibly(5, TimeUnit.SECONDS);
        });

        return customers;
    }

}
