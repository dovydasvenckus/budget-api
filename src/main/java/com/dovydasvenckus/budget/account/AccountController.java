package com.dovydasvenckus.budget.account;

import com.dovydasvenckus.budget.util.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.dovydasvenckus.budget.ResourceMapping.ACCOUNT_RESOURCE;

@RestController
@RequestMapping(ACCOUNT_RESOURCE)
public class AccountController {

    private AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public List<AccountDTO> getAccounts() {
        return accountService.getAccounts();
    }

    @GetMapping(value = "/{id}")
    public AccountDTO getAccount(@PathVariable("id") Long accountId) {
        return accountService.getAccount(accountId);
    }

    @PostMapping
    public ResponseEntity<Void> createAccount(@Valid @RequestBody AccountDTO account) {
        AccountDTO createdAccount = accountService.createAccount(account);

        return ResponseBuilder.created(ACCOUNT_RESOURCE, createdAccount.getId());
    }
}
