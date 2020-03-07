package com.dovydasvenckus.budget.integration;

import com.dovydasvenckus.budget.account.AccountDTO;
import com.dovydasvenckus.budget.account.AccountType;
import com.dovydasvenckus.budget.client.ClientDTO;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.UUID;

import static com.dovydasvenckus.budget.ResourceMapping.CLIENT_RESOURCE;
import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

@Component
@Scope(value = SCOPE_PROTOTYPE)
public class John {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private Jdbi jdbi;

    private String username = "johny3000";

    private String firstName = "John";

    private String lastName = "Smith";


    public ResponseEntity<Void> register() {
        return restTemplate.postForEntity(CLIENT_RESOURCE, new ClientDTO(username, firstName, lastName), Void.class);
    }

    public ResponseEntity<ClientDTO> getClientInfo(URI uri) {
        return restTemplate.getForEntity(uri, ClientDTO.class);
    }

    public ResponseEntity<Void> openAccount(String accountName, AccountType accountType) {
        return restTemplate.postForEntity(
                CLIENT_RESOURCE + "/" + username + "/accounts",
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
                CLIENT_RESOURCE + "/" + username + "/accounts",
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
        jdbi.useHandle(this::deleteAllData);
    }

    private void deleteAllData(Handle handle) {
        UUID clientId = handle.createQuery("SELECT id FROM clients WHERE username = :username")
                .bind("username", username)
                .mapTo(UUID.class)
                .one();

        handle.createUpdate("DELETE FROM accounts WHERE client_id = :clientId")
                .bind("clientId", clientId)
                .execute();

        handle.createUpdate("DELETE FROM clients WHERE id = :clientId")
                .bind("clientId", clientId)
                .execute();
    }
}
