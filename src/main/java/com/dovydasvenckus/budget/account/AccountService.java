package com.dovydasvenckus.budget.account;

import com.dovydasvenckus.budget.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
public class AccountService {

    private AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Optional<AccountDTO> getAccountDTO(Long id) {
        return accountRepository.findById(id)
                .map(AccountDTO::new);
    }

    public Optional<Account> getAccount(Long id) {
        return accountRepository.findById(id);
    }

    public List<AccountDTO> getAccounts() {
        return accountRepository
                .findAll()
                .stream()
                .map(AccountDTO::new)
                .collect(toList());
    }

    @Transactional
    public AccountDTO createAccount(AccountDTO accountDTO) {
        Account account = new Account(accountDTO);
        return new AccountDTO(accountRepository.save(account));
    }

    @Transactional
    public AccountDTO createAccount(Client client, AccountDTO accountDTO) {
        Account account = new Account(accountDTO);

        account.setClient(client);
        Account createdAccount = accountRepository.save(account);

        client.addAccount(createdAccount);

        return new AccountDTO(createdAccount);
    }

    @Transactional
    public void updateAccount(Account account, AccountDTO updatedAccount) {
        account.setName(updatedAccount.getName());
        account.setType(updatedAccount.getType());
    }

    @Transactional
    public void deleteAllAccounts(String username) {
        accountRepository.deleteAllByClientUsername(username);
    }
}
