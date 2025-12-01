package com.java.virtusa.dao;

import com.java.virtusa.model.Accounts;

import java.util.Collection;

public interface BankDao {
    void addAccount(Accounts account);
    Accounts getAccount(String accountNumber) throws Exception;
    Collection<Accounts> getAllAccounts();
    void removeAccount(String accountNumber) throws Exception;
}
