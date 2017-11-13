package com.dovydasvenckus.budget.integration;

import com.dovydasvenckus.budget.account.AccountDTO;
import com.dovydasvenckus.budget.account.AccountService;
import com.dovydasvenckus.budget.account.AccountType;
import com.dovydasvenckus.budget.client.ClientDTO;
import com.dovydasvenckus.budget.client.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

@Component
@Scope(value = SCOPE_PROTOTYPE)
public class John {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ClientService clientService;

    @Autowired
    private AccountService accountService;

    private String username = "johny3000";

    private String firstName = "John";

    private String lastName = "Smith";


    public ResponseEntity<Void> register() {
        return restTemplate.postForEntity("/api/clients/", new ClientDTO(username, firstName, lastName), Void.class);
    }

    public ResponseEntity<ClientDTO> getClientInfo(URI uri) {
        return restTemplate.getForEntity(uri, ClientDTO.class);
    }

    public ResponseEntity<Void> openAccount(String accountName, AccountType accountType) {
        return restTemplate.postForEntity(
                "/api/clients/" + username + "/accounts",
                new AccountDTO(accountName, accountType),
                Void.class);
    }

    public AccountDTO getLastAccount() {
        Collection<AccountDTO> accounts = getAccounts();

        return accounts.stream()
                .sorted(Comparator.comparing(AccountDTO::getId).reversed())
                .findFirst()
                .orElse(null);
    }

    public Collection<AccountDTO> getAccounts() {
        return Arrays.asList(restTemplate.getForObject(
                "/api/clients/" + username + "/accounts",
                AccountDTO[].class)
        );
    }

    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void cleanUp() {
        accountService.deleteAllAccounts(username);
        clientService.deleteClient(username);
    }
}
