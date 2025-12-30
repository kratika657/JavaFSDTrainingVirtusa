package com.java.bank.client;

import com.java.bank.dto.AccountDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "ACCOUNT-SERVICE")
public interface AccountClient {
    @PutMapping("/api/accounts/{accountNumber}/balance")
    AccountDTO updateBalance(@PathVariable String accountNumber,
                             @RequestParam double amount);

    @GetMapping("/api/accounts/{accountNumber}")
    AccountDTO getAccount(@PathVariable String accountNumber);
}
