package com.dovydasvenckus.budget.client;

import com.dovydasvenckus.budget.account.AccountDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class AccountResourceTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void shouldReturnEmptyListWhenThereIsNoClients() {
        ResponseEntity<Client[]> accounts = restTemplate.getForEntity("/api/clients", Client[].class);

        assertThat(accounts.getBody().length, is(0));
    }
}