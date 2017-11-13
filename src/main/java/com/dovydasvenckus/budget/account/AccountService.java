package com.dovydasvenckus.budget.account;

import com.dovydasvenckus.budget.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class AccountService {

    private AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public AccountDTO getAccount(Long id) {
        return new AccountDTO(accountRepository.findOne(id));
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
    public void deleteAllAccounts(String username) {
        accountRepository.deleteAllByClientUsername(username);
    }
}
