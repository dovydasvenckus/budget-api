package com.dovydasvenckus.budget.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class AccountService {

    private AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public AccountDto getAccount(Long id) {
        return new AccountDto(accountRepository.findOne(id));
    }

    public List<AccountDto> getAccounts() {
        return accountRepository
                .findAll()
                .stream()
                .map(AccountDto::new)
                .collect(toList());
    }

    public AccountDto createAccount(AccountDto accountDto) {
        Account account = new Account();
        account.setName(accountDto.getName());
        account.setType(accountDto.getType());

        return new AccountDto(accountRepository.save(account));
    }
}
