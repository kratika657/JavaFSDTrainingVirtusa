package com.java.virtusa.dao;

import com.java.virtusa.exception.AccountNotFoundException;
import com.java.virtusa.model.Accounts;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BankDaoImpl implements BankDao {
    private Map<String, Accounts> accounts = new HashMap<>();

    @Override
    public void addAccount(Accounts account) {
        accounts.put(account.getAccountNumber(), account);
    }

    @Override
    public Accounts getAccount(String accountNumber) throws AccountNotFoundException {
        Accounts acc = accounts.get(accountNumber);
        if (acc == null) throw new AccountNotFoundException("Account number doesn not exist");
        return acc;
    }

    @Override
    public Collection<Accounts> getAllAccounts() {
        return accounts.values();
    }

    @Override
    public void removeAccount(String accountNumber) throws Exception {
        Accounts acc = getAccount(accountNumber);
        acc.closeAccount();
    }
}
