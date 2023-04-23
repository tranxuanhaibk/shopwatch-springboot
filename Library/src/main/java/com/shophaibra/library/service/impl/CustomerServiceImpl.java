package com.shophaibra.library.service.impl;

import com.shophaibra.library.dto.CustomerDto;
import com.shophaibra.library.model.Customer;
import com.shophaibra.library.model.Role;
import com.shophaibra.library.repository.CustomerRepository;
import com.shophaibra.library.repository.RoleRepository;
import com.shophaibra.library.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private RoleRepository repository;
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public CustomerDto save(CustomerDto customerDto) {
        Customer customer = new Customer();
        customer.setFirstName(customerDto.getFirstName());
        customer.setLastName(customerDto.getLastName());
        customer.setUsername(customerDto.getUsername());
        customer.setPassword(customerDto.getPassword());
        customer.setRoles(Arrays.asList(repository.findByName("CUSTOMER")));

        Customer customerSave = customerRepository.save(customer);
        return mapperDto(customerSave);
    }

    @Override
    public Customer findByUsername(String username) {
        return customerRepository.findByUsername(username);
    }

    private CustomerDto mapperDto(Customer customer) {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setFirstName(customer.getFirstName());
        customerDto.setLastName(customer.getLastName());
        customerDto.setUsername(customer.getUsername());
        return customerDto;
    }
}
