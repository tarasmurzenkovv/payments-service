package com.payments.service.service.validation;

import com.payments.service.model.Customer;
import com.payments.service.model.exceptions.CustomerException;

import static com.payments.service.http.HttpStatusCode.BAD_REQUEST;
import static com.payments.service.utils.StringUtils.isEmpty;

public class CustomerValidationService implements ValidationService<Customer> {
    public void validate(Customer customer) {
        if (isEmpty(customer.getName())) {
            throw new CustomerException("Cannot create customer with empty name", BAD_REQUEST);
        }
    }
}
