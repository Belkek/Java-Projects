package com.fdmgroup.skillslab.hk.ood.sprintassessement.sprint2.java.service;

import com.fdmgroup.skillslab.hk.ood.sprintassessement.sprint1.model.Account;
import com.fdmgroup.skillslab.hk.ood.sprintassessement.sprint2.java.dao.AccountReaderDAO;
import com.fdmgroup.skillslab.hk.ood.sprintassessement.sprint2.java.dao.AccountWriterDAO;
import com.fdmgroup.skillslab.hk.ood.sprintassessement.sprint2.java.model.StandardAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

    @Mock
    private AccountReaderDAO accountReaderDAO;

    @Mock
    private AccountWriterDAO accountWriterDAO;

    private AccountService accountService;

    @BeforeEach
    void setUp() {
        accountService = new AccountServiceImpl(accountReaderDAO, accountWriterDAO);
    }

    @Test
    @DisplayName("getAccounts should return the list of accounts received from AccountReaderDAO's readAccounts method")
    void getAccounts_ShouldReturnListFromAccountReaderDAO() {
        // Arrange
        Account account1 = new StandardAccount("John Doe", 1500.00);
        Account account2 = new StandardAccount("Jane Smith", 2500.00);
        List<Account> expectedAccounts = Arrays.asList(account1, account2);
        when(accountReaderDAO.readAccounts()).thenReturn(expectedAccounts);

        // Act
        List<Account> actualAccounts = accountService.getAccounts();

        // Assert
        assertEquals(expectedAccounts, actualAccounts);
        verify(accountReaderDAO, times(1)).readAccounts();
    }

    @Test
    @DisplayName("createAccount should call AccountWriterDAO's createAccount method with the same account object")
    void createAccount_ShouldCallAccountWriterDAOCreateAccountWithSameObject() {
        // Arrange
        Account accountToCreate = new StandardAccount("Bob Wilson", 3000.00);

        // Act
        accountService.createAccount(accountToCreate);

        // Assert
        verify(accountWriterDAO, times(1)).createAccount(accountToCreate);
    }

    @Test
    @DisplayName("removeAccount should call AccountWriterDAO's deleteAccount method with the same account object")
    void removeAccount_ShouldCallAccountWriterDAODeleteAccountWithSameObject() {
        // Arrange
        Account accountToRemove = new StandardAccount("Charlie Davis", 1000.00);

        // Act
        accountService.removeAccount(accountToRemove);

        // Assert
        verify(accountWriterDAO, times(1)).deleteAccount(accountToRemove);
    }
}