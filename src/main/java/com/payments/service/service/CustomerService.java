package com.payments.service.service;

import com.payments.service.dao.CustomerDao;
import com.payments.service.model.Customer;
import com.payments.service.model.exceptions.CustomerException;
import com.payments.service.service.validation.ValidationService;

import javax.inject.Inject;

import static com.payments.service.model.Customer.of;
import static java.lang.String.format;

public class CustomerService {
    private final CustomerDao customerDao;
    private final ValidationService<Customer> validationService;

    @Inject
    public CustomerService(CustomerDao customerDao, ValidationService<Customer> validationService) {
        this.customerDao = customerDao;
        this.validationService = validationService;
    }

    public Customer create(Customer customer) {
        validationService.validate(customer);
        var name = customer.getName();
        var customerId = customerDao.createCustomer(name);
        return of(customerId, name);
    }

    public Customer find(int id) {
        return customerDao.findCustomerById(id)
                .orElseThrow(() -> new CustomerException(format("Cannot find customer with id '%s'", id)));
    }

    public int delete(int id) {
        return customerDao.deleteById(id);
    }
}
