package com.dovydasvenckus.budget.client;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class ClientResourceTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void shouldReturnEmptyListWhenThereIsNoClients() {
        ResponseEntity<Client[]> clients = restTemplate.getForEntity("/api/clients", Client[].class);

        assertThat(clients.getStatusCode(), is(HttpStatus.OK));
        assertThat(clients.getBody().length, is(0));
    }

    @Test
    public void shouldCreateClient() {
        Client client = new Client("John");
        HttpEntity<Client> request = new HttpEntity<>(client);

        ResponseEntity response = restTemplate.postForEntity("/api/clients", request, Client.class);

        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
        assertThat(response.getHeaders().getLocation().toString(), is("/api/accounts/1"));
    }
}
