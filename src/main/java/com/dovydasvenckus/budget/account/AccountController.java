package com.dovydasvenckus.budget.account;

import com.dovydasvenckus.budget.util.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.dovydasvenckus.budget.ResourceMapping.ACCOUNT_RESOURCE;

@RestController
@RequestMapping(ACCOUNT_RESOURCE)
class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public List<AccountDTO> getAccounts() {
        return accountService.getAccounts();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<AccountDTO> getAccount(@PathVariable("id") UUID accountId) {
        Optional<AccountDTO> foundAccount = accountService.getAccountDTO(accountId);

        return ResponseEntity.of(foundAccount);
    }

    @PostMapping
    public ResponseEntity<Void> createAccount(@Valid @RequestBody AccountDTO account) {
        AccountDTO createdAccount = accountService.createAccount(account);

        return ResponseBuilder.created(ACCOUNT_RESOURCE, createdAccount.getId());
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> updateAccount(@PathVariable("id") UUID accountId,
                                              @Valid @RequestBody AccountDTO updatedAccount) {
        Optional<Account> currentAccount = accountService.getAccount(accountId);

        if (currentAccount.isPresent()) {
            accountService.updateAccount(currentAccount.get(), updatedAccount);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }
}
