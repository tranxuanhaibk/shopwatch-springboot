package com.shophaibra.library.service;

import com.shophaibra.library.dto.CustomerDto;
import com.shophaibra.library.model.Customer;

public interface CustomerService {
    CustomerDto save(CustomerDto customerDto);

    Customer findByUsername(String username);
}
