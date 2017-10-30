package com.dovydasvenckus.budget.account;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.dovydasvenckus.budget.account.AccountType.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

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
        AccountDto accountToCreate = new AccountDto("My assets", ASSET);

        AccountDto createdAccount = accountService.createAccount(accountToCreate);

        Account account = accountRepository.findOne(createdAccount.getId());

        assertThat(account.getName(), is("My assets"));
        assertThat(account.getType(), is(ASSET));
    }


    @Test
    public void shouldGetAllAccounts() {
        createAccount("Income", INCOME);
        createAccount("Expense", EXPENSE);

        List<AccountDto> accounts = accountService.getAccounts();

        assertThat(accounts.size(), is(2));
        assertAccountNameAndType(accounts.get(0), "Income", INCOME);
        assertAccountNameAndType(accounts.get(1), "Expense", EXPENSE);
    }

    private void createAccount(String name, AccountType accountType) {
        accountRepository.save(new Account(name, accountType));
    }

    private void assertAccountNameAndType(AccountDto account, String name, AccountType type) {
        assertThat(account.getName(), is(name));
        assertThat(account.getType(), is(type));
    }
}
