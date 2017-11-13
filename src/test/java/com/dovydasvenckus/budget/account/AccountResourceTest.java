package com.dovydasvenckus.budget.account;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static com.dovydasvenckus.budget.account.AccountType.ASSET;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

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

        assertThat(accounts.getStatusCode(), is(HttpStatus.OK));
        assertThat(accounts.getBody().length, is(0));
    }

    @Test
    public void shouldCreateAccount() {
        AccountDTO accountDTO = new AccountDTO("Assets", ASSET);
        HttpEntity<AccountDTO> request = new HttpEntity<>(accountDTO);

        ResponseEntity<AccountDTO> response = restTemplate.postForEntity("/api/accounts/", request, AccountDTO.class);

        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
        assertThat(response.getHeaders().getLocation().toString(), is("/api/accounts/1"));
    }

    @Test
    public void shouldNotCreateAccountWithEmptyName() {
        AccountDTO accountDTO = new AccountDTO("", ASSET);
        HttpEntity<AccountDTO> request = new HttpEntity<>(accountDTO);

        ResponseEntity<AccountDTO> response = restTemplate.postForEntity("/api/accounts", request, AccountDTO.class);

        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }
}
