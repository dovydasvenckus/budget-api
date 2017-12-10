package com.dovydasvenckus.budget.client;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static com.dovydasvenckus.budget.ResourceMapping.CLIENT_RESOURCE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.OK;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class ClientResourceTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void shouldReturnEmptyListWhenThereIsNoClients() {
        ResponseEntity<Client[]> clients = restTemplate.getForEntity(CLIENT_RESOURCE, Client[].class);

        assertThat(clients.getStatusCode()).isEqualTo(OK);
        assertThat(clients.getBody()).isEmpty();
    }
}
