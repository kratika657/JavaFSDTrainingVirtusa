package com.java.spr.banksimulatorproject_phase1.repo;

import com.java.spr.banksimulatorproject_phase1.model.Account;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AccountRepository extends MongoRepository<Account, String> {
    Optional<Account> findByAccountNumber(String accountNumber);
    boolean existsByAccountNumber(String accountNumber);
    void deleteByAccountNumber(String accountNumber);
}
