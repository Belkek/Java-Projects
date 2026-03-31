package com.fdmgroup.skillslab.hk.ood.sprintassessement.sprint2.java.service;

import com.fdmgroup.skillslab.hk.ood.sprintassessement.sprint1.model.Account;
import com.fdmgroup.skillslab.hk.ood.sprintassessement.sprint2.java.dao.AccountReaderDAO;
import com.fdmgroup.skillslab.hk.ood.sprintassessement.sprint2.java.dao.AccountWriterDAO;

import java.util.List;

public class AccountServiceImpl implements AccountService {

    private final AccountReaderDAO accountReaderDAO;
    private final AccountWriterDAO accountWriterDAO;

    public AccountServiceImpl(AccountReaderDAO accountReaderDAO, AccountWriterDAO accountWriterDAO) {
        this.accountReaderDAO = accountReaderDAO;
        this.accountWriterDAO = accountWriterDAO;
    }

    @Override
    public List<Account> getAccounts() {
        return accountReaderDAO.readAccounts();
    }

    @Override
    public void createAccount(Account account) {
        accountWriterDAO.createAccount(account);
    }

    @Override
    public void removeAccount(Account account) {
        accountWriterDAO.deleteAccount(account);
    }
}