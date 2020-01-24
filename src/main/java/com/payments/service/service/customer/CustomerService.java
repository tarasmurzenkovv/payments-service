package com.payments.service.service.customer;

import com.payments.service.dao.CustomerDao;
import com.payments.service.model.Customer;
import com.payments.service.model.exceptions.CustomerException;
import com.payments.service.service.customer.validation.ValidationService;

import javax.inject.Inject;

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
        String name = customer.getName();
        int customerId = customerDao.createCustomer(name);
        return Customer.of(customerId, name);
    }

    public Customer find(int id) {
        return customerDao.findCustomerById(id)
                .orElseThrow(() -> new CustomerException(String.format("Cannot find customer with id '%s'", id)));
    }

    public int delete(int id) {
        return customerDao.deleteById(id);
    }
}
