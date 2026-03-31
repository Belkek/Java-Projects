package com.fdmgroup.skillslab.hk.ood.sprintassessement.sprint2.java.dao;

import com.fdmgroup.skillslab.hk.ood.sprintassessement.sprint1.model.Account;

import java.util.List;

public interface AccountReaderDAO {
    List<Account> readAccounts();
}