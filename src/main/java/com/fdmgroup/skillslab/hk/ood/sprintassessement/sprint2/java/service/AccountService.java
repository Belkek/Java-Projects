package com.fdmgroup.skillslab.hk.ood.sprintassessement.sprint2.java.service;

import com.fdmgroup.skillslab.hk.ood.sprintassessement.sprint1.model.Account;

import java.util.List;

public interface AccountService {
    List<Account> getAccounts();
    void createAccount(Account account);
    void removeAccount(Account account);
}