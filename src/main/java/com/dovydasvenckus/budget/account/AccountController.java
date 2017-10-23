package com.dovydasvenckus.budget.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @RequestMapping(method = GET)
    public List<AccountDto> getAccounts() {
        return accountService.getAccounts();
    }

    @RequestMapping(method = POST)
    public AccountDto createAccount(@RequestBody AccountDto account) {
        return accountService.createAccount(account);
    }
}
