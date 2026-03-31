package com.fdmgroup.skillslab.hk.ood.sprintassessement.sprint2.java.dao;

import com.fdmgroup.skillslab.hk.ood.sprintassessement.sprint1.model.Account;

public interface AccountWriterDAO {
    void createAccount(Account account);
    void deleteAccount(Account account);
}