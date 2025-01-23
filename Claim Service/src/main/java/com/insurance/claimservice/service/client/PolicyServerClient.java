package com.insurance.claimservice.service.client;

import com.insurance.claimservice.dto.PolicyDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "policy-service",url = "http://localhost:8082")
public interface PolicyServerClient {

    @GetMapping("/api/policies/{id}")
    PolicyDTO getPolicyById(@PathVariable Long id);
}
