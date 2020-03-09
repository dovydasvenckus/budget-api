package com.dovydasvenckus.budget.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Optional<AccountDTO> getAccountDTO(UUID id) {
        return accountRepository.findById(id)
                .map(AccountDTO::new);
    }

    public Optional<Account> getAccount(UUID id) {
        return accountRepository.findById(id);
    }

    public List<AccountDTO> getAccounts() {
        return accountRepository
                .findAll()
                .stream()
                .map(AccountDTO::new)
                .collect(toList());
    }

    public Collection<AccountDTO> getAccountsByUsername(String username) {
        return accountRepository.findAllByUsername(username)
                .stream()
                .map(AccountDTO::new)
                .collect(toList());
    }

    @Transactional
    public AccountDTO createAccount(AccountDTO accountDTO) {
        Account account = new Account(accountDTO);
        accountRepository.save(account);
        return new AccountDTO(account);
    }

    @Transactional
    public AccountDTO createAccount(UUID userId, AccountDTO accountDTO) {
        Account account = new Account(accountDTO);

        account.setUserId(userId);
        accountRepository.save(account);

        return new AccountDTO(account);
    }

    @Transactional
    public void updateAccount(Account account, AccountDTO updatedAccount) {
        account.setName(updatedAccount.getName());
        account.setType(updatedAccount.getType());
    }

}
