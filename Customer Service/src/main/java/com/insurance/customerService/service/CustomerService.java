package com.insurance.customerService.service;

import com.insurance.customerService.model.Customer;
import com.insurance.customerService.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    public Customer createCustomer(Customer customer) {
        Customer savedCustomer = customerRepository.save(customer);
        String massage = "Customer created: " + savedCustomer.getId() + ", " + savedCustomer.getName();

        kafkaTemplate.send("customers", massage);
        return savedCustomer;
    }

    public Customer updateCustomer(Long id, Customer customer) {

        Customer updatedCustomer = customerRepository.findById(id).orElseThrow(() -> new RuntimeException("Customer not found"));

        updatedCustomer.setName(customer.getName());
        updatedCustomer.setEmail(customer.getEmail());
        updatedCustomer.setPhone(customer.getPhone());
        updatedCustomer.setDateOfBirth(customer.getDateOfBirth());

        return customerRepository.save(updatedCustomer);
    }

    public void deleteCustomer(Long id) {

        customerRepository.deleteById(id);
    }
}
