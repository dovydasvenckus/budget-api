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
        ResponseEntity<AccountDto[]> accounts = restTemplate.getForEntity("/api/accounts", AccountDto[].class);

        assertThat(accounts.getBody().length, is(0));
    }

    @Test
    public void shouldCreateAccount() {
        AccountDto accountDto = new AccountDto("Assets", ASSET);
        HttpEntity<AccountDto> request = new HttpEntity<>(accountDto);

        ResponseEntity<AccountDto> response = restTemplate.postForEntity("/api/accounts", request, AccountDto.class);

        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
        assertThat(response.getHeaders().getLocation().toString(), is("/api/accounts/1"));
    }
}
