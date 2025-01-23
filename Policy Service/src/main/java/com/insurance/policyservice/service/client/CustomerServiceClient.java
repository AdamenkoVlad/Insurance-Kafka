package com.insurance.policyservice.service.client;

import com.insurance.policyservice.dto.CustomerDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "customer-service", url = "http://localhost:8080")
public interface CustomerServiceClient {

    @GetMapping("/api/customers/{id}")
    CustomerDTO getCustomerById(@PathVariable Long id);
}
