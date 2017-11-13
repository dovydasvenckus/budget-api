package com.dovydasvenckus.budget.account;

import com.dovydasvenckus.budget.util.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import static com.dovydasvenckus.budget.ResourceMapping.ACCOUNT_RESOURCE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping(ACCOUNT_RESOURCE)
public class AccountController {

    private AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @RequestMapping(method = GET)
    public List<AccountDTO> getAccounts() {
        return accountService.getAccounts();
    }

    @RequestMapping(value = "/{id}", method = GET)
    public AccountDTO getAccount(@PathVariable("id") Long accountId) {
        return accountService.getAccount(accountId);
    }

    @RequestMapping(method = POST)
    public ResponseEntity<Void> createAccount(@Valid @RequestBody AccountDTO account) {
        AccountDTO createdAccount = accountService.createAccount(account);

        return ResponseBuilder.created(ACCOUNT_RESOURCE, createdAccount.getId());
    }
}
