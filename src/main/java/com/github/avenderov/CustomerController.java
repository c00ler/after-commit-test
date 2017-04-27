package com.github.avenderov;

import com.google.common.base.Stopwatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);

    private final CustomerService customerService;

    public CustomerController(final CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public Collection<Customer> getAll() {
        final Stopwatch stopwatch = Stopwatch.createStarted();
        final Collection<Customer> customers = customerService.findAll();

        LOGGER.info("Got {} customers in {}", customers.size(), stopwatch.toString());

        return customers;
    }

}
