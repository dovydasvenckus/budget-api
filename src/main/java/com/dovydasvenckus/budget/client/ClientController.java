package com.dovydasvenckus.budget.client;

import com.dovydasvenckus.budget.account.AccountDTO;
import com.dovydasvenckus.budget.account.AccountService;
import com.dovydasvenckus.budget.util.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Optional;

import static com.dovydasvenckus.budget.ResourceMapping.ACCOUNT_RESOURCE;
import static com.dovydasvenckus.budget.ResourceMapping.CLIENT_RESOURCE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping(CLIENT_RESOURCE)
public class ClientController {

    private ClientService clientService;

    private AccountService accountService;

    @Autowired
    public ClientController(ClientService clientService, AccountService accountService) {
        this.clientService = clientService;
        this.accountService = accountService;
    }

    @RequestMapping(method = GET)
    public ResponseEntity<Collection<ClientDTO>> getClients() {
        return ResponseEntity.ok().body(clientService.getAllClients());
    }

    @RequestMapping("/{clientId}")
    public ResponseEntity<ClientDTO> getClientById(@PathVariable long clientId) {
        return clientService.getClientById(clientId)
                .map(clientDTO -> ResponseEntity.ok().body(clientDTO))
                .orElse(ResponseEntity.notFound().build());
    }

    @RequestMapping(method = POST)
    public ResponseEntity createClient(@Valid @RequestBody ClientDTO clientToCreate) {
        Client createdClient = clientService.createClient(clientToCreate);

        return ResponseBuilder.created(CLIENT_RESOURCE, createdClient.getId());
    }

    @RequestMapping(value = "/{username}/accounts", method = GET)
    public ResponseEntity<Collection<AccountDTO>> createAccountForClient(@PathVariable String username) {
        Optional<Client> foundClient = clientService.getClientByUsername(username);

        return foundClient
                .map(client -> ResponseEntity.ok(clientService.transformClientAccounts(client)))
                .orElse(ResponseEntity.notFound().build());
    }

    @RequestMapping(value = "/{username}/accounts", method = POST)
    public ResponseEntity<Void> createAccountForClient(@PathVariable String username,
                                                       @Valid @RequestBody AccountDTO accountDTO) {
        Optional<Client> client = clientService.getClientByUsername(username);

        return client.map(c ->
                createResponse(accountService.createAccount(c, accountDTO)))
                .orElse(ResponseEntity.notFound().build());
    }

    private ResponseEntity<Void> createResponse(AccountDTO accountDTO) {
        return ResponseBuilder.created(ACCOUNT_RESOURCE, accountDTO.getId());
    }
}
