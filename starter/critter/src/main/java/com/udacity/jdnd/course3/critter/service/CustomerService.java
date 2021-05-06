package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer getCustomer(long customerId) {
        Customer customer;
        Optional<Customer> byId = customerRepository.findById(customerId);
        if(byId.isPresent()){
            customer = byId.get();
            return customer;
        } else {
            throw new NoSuchElementException("Employee with id:" + customerId + " can not be found");
        }
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
}
