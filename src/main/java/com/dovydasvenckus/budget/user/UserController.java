package com.dovydasvenckus.budget.user;

import com.dovydasvenckus.budget.account.AccountDTO;
import com.dovydasvenckus.budget.account.AccountService;
import com.dovydasvenckus.budget.util.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import static com.dovydasvenckus.budget.ResourceMapping.ACCOUNT_RESOURCE;
import static com.dovydasvenckus.budget.ResourceMapping.USER_RESOURCE;

@RestController
@RequestMapping(USER_RESOURCE)
class UserController {

    private final UserService userService;

    private final AccountService accountService;

    @Autowired
    public UserController(UserService userService, AccountService accountService) {
        this.userService = userService;
        this.accountService = accountService;
    }

    @GetMapping
    public ResponseEntity<Collection<UserDTO>> getUsers() {
        return ResponseEntity.ok().body(userService.getAllUsers());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable UUID userId) {
        return userService.getUserById(userId)
                .map(userDTO -> ResponseEntity.ok().body(userDTO))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/{username}/accounts")
    public ResponseEntity<Collection<AccountDTO>> createAccountForUser(@PathVariable String username) {
        Optional<User> foundUser = userService.getUserByUsername(username);

        return foundUser
                .map(user -> ResponseEntity.ok(accountService.getAccountsByUsername(user.getUsername())))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(value = "/{username}/accounts")
    public ResponseEntity<Void> createAccountForUser(@PathVariable String username,
                                                     @Valid @RequestBody AccountDTO accountDTO) {
        Optional<User> user = userService.getUserByUsername(username);

        return user.map(c ->
                createResponse(accountService.createAccount(c.getId(), accountDTO)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity createUser(@Valid @RequestBody UserDTO userToCreate) {
        User createdUser = userService.createUser(userToCreate);

        return ResponseBuilder.created(USER_RESOURCE, createdUser.getId());
    }

    private ResponseEntity<Void> createResponse(AccountDTO accountDTO) {
        return ResponseBuilder.created(ACCOUNT_RESOURCE, accountDTO.getId());
    }

}
