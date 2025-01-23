package com.insurance.policyservice.service;

import com.insurance.policyservice.dto.CustomerDTO;
import com.insurance.policyservice.model.Policy;
import com.insurance.policyservice.repository.PolicyRepository;
import com.insurance.policyservice.service.client.CustomerServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PolicyService {

    @Autowired
    private PolicyRepository policyRepository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private CustomerServiceClient customerServiceClient;

    public List<Policy> getAllPolicies() {
        return policyRepository.findAll();
    }

    public Optional<Policy> getPolicyById(Long id) {
        return policyRepository.findById(id);
    }

    public Policy createPolicy(Policy policy) {
        CustomerDTO customerDTO = customerServiceClient.getCustomerById(policy.getCustomerId());
        if (customerDTO == null) {
            throw new RuntimeException("Customer not found");
        }

        Policy savedPolicy = policyRepository.save(policy);

        String message = "Policy created: " + savedPolicy.getId() + ", " + savedPolicy.getPolicyNumber();
        kafkaTemplate.send("policy-created", message);

        return savedPolicy;
    }

    public Policy updatePolicy(Long id, Policy policyUpdate) {

        Policy policy = policyRepository.findById(id).orElseThrow(() -> new RuntimeException("Policy not found"));
        policy.setPolicyNumber(policyUpdate.getPolicyNumber());
        policy.setStartDate(policyUpdate.getStartDate());
        policy.setEndDate(policyUpdate.getEndDate());
        policy.setPremiumAmount(policyUpdate.getPremiumAmount());
        policy.setCustomerId(policyUpdate.getCustomerId());
        return policyRepository.save(policy);
    }

    public void deletePolicy(Long id) {
        policyRepository.deleteById(id);
    }

}
