package com.dovydasvenckus.budget.account;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.dovydasvenckus.budget.account.AccountType.*;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class AccountServiceTest {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void shouldCreateAccount() {
        AccountDTO accountToCreate = new AccountDTO("My assets", ASSET);

        AccountDTO createdAccount = accountService.createAccount(accountToCreate);

        Account account = accountRepository.findOne(createdAccount.getId());

        assertThat(account.getName()).isEqualTo(("My assets"));
        assertThat(account.getType()).isEqualTo(ASSET);
    }


    @Test
    public void shouldGetAllAccounts() {
        createAccount("Income", INCOME);
        createAccount("Expense", EXPENSE);

        List<AccountDTO> accounts = accountService.getAccounts();

        assertThat(accounts.size()).isEqualTo(2);
        assertAccountNameAndType(accounts.get(0), "Income", INCOME);
        assertAccountNameAndType(accounts.get(1), "Expense", EXPENSE);
    }

    private void createAccount(String name, AccountType accountType) {
        accountRepository.save(new Account(name, accountType));
    }

    private void assertAccountNameAndType(AccountDTO account, String name, AccountType type) {
        assertThat(account.getName()).isEqualTo(name);
        assertThat(account.getType()).isEqualTo(type);
    }
}
