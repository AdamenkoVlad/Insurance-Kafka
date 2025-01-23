package com.insurance.claimservice.service;

import com.insurance.claimservice.dto.PolicyDTO;
import com.insurance.claimservice.model.Claim;
import com.insurance.claimservice.repository.ClaimRepository;
import com.insurance.claimservice.service.client.PolicyServerClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClaimService {

    @Autowired
    private ClaimRepository claimRepository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private PolicyServerClient policyServerClient;

    public List<Claim> getAllClaims() {
        return claimRepository.findAll();
    }

    public Optional<Claim> getClaimById(Long id) {
        return claimRepository.findById(id);
    }

    public Claim createClaim(Claim claim) {

        PolicyDTO policy = policyServerClient.getPolicyById(claim.getPolicyId());
        if (policy == null) {
            throw new RuntimeException("Policy not found");
        }

        Claim savedClaim = claimRepository.save(claim);

        String message = "Claim created: " + savedClaim.getId() + ", " + savedClaim.getDescription();
        kafkaTemplate.send("claim-created", message);

        return savedClaim;

    }

    public Claim updateClaim(Claim updateClaim, Long id){
        Claim claim = claimRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Claim not found"));
        claim.setDescription(updateClaim.getDescription());
        claim.setClaimAmount(updateClaim.getClaimAmount());
        claim.setStatus(updateClaim.getStatus());
        claim.setPolicyId(updateClaim.getPolicyId());
        return claimRepository.save(claim);
    }

    public void deleteClaim(Long id){
        claimRepository.deleteById(id);
    }


}
