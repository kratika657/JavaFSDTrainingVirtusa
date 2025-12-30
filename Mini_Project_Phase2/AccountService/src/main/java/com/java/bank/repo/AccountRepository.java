package com.java.bank.repo;

import com.java.bank.model.Account;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AccountRepository extends MongoRepository<Account,String> {
    Account findByAccountNumber(String accountNumber);
}
