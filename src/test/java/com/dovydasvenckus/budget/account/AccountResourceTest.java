package com.dovydasvenckus.budget.account;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;

import static com.dovydasvenckus.budget.account.AccountType.ASSET;
import static com.dovydasvenckus.budget.account.AccountType.EXPENSE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class AccountResourceTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private AccountRepository accountRepository;

    @Before
    public void setup() {
        accountRepository.deleteAll();
    }

    @Test
    public void shouldReturnNoAccounts() {
        ResponseEntity<AccountDTO[]> accounts = restTemplate.getForEntity("/api/accounts", AccountDTO[].class);

        assertThat(accounts.getStatusCode()).isEqualTo(OK);
        assertThat(accounts.getBody()).isEmpty();
    }

    @Test
    public void shouldCreateAccount() {
        ResponseEntity<AccountDTO> response = createAccount("Assets", ASSET);

        assertThat(response.getStatusCode()).isEqualTo(CREATED);
        assertThat(response.getHeaders().getLocation().toString()).isEqualTo("/api/accounts/1");
    }

    @Test
    public void shouldNotCreateAccountWithEmptyName() {
        ResponseEntity<AccountDTO> response = createAccount("", ASSET);

        assertThat(response.getStatusCode()).isEqualTo(BAD_REQUEST);
    }

    @Test
    public void shouldUpdateAccountNameAndType() {
        ResponseEntity<AccountDTO> createAccountResponse = createAccount("Assets", ASSET);

        URI createdAccountUri = createAccountResponse.getHeaders().getLocation();

        AccountDTO accountDTO = new AccountDTO("Rent", EXPENSE);

        restTemplate.exchange(createdAccountUri, HttpMethod.PUT, new HttpEntity<>(accountDTO), Void.class);

        AccountDTO updatedAccount = restTemplate.getForEntity(createdAccountUri, AccountDTO.class).getBody();

        assertThat(updatedAccount.getName()).isEqualTo("Rent");
        assertThat(updatedAccount.getType()).isEqualTo(EXPENSE);
    }

    @Test
    public void shouldReturn404WhenUpdatingAccountIsNotFound() {
        AccountDTO accountDTO = new AccountDTO("Rent", EXPENSE);

        ResponseEntity<Void> updateResponse = restTemplate.exchange("/api/accounts/9999", HttpMethod.PUT, new HttpEntity<>(accountDTO), Void.class);

        assertThat(updateResponse.getStatusCode()).isEqualTo(NOT_FOUND);
    }

    private ResponseEntity<AccountDTO> createAccount(String name, AccountType type) {
        AccountDTO accountDTO = new AccountDTO(name, type);
        HttpEntity<AccountDTO> request = new HttpEntity<>(accountDTO);

        return restTemplate.postForEntity("/api/accounts/", request, AccountDTO.class);
    }
}
