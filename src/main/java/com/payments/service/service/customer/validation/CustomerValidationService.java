package com.payments.service.service.customer.validation;

import com.payments.service.model.Customer;
import com.payments.service.model.exceptions.CustomerException;
import com.payments.service.utils.StringUtils;

public class CustomerValidationService implements ValidationService<Customer> {
    public void validate(Customer customer) {
        if (StringUtils.isEmpty(customer.getName())) {
            throw new CustomerException("Cannot create customer with empty name");
        }
    }
}
