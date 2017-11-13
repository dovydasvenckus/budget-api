package com.dovydasvenckus.budget.client;

import com.dovydasvenckus.budget.account.AccountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientService {

    private ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    Optional<ClientDTO> getClientById(long clientId) {
        return clientRepository
                .getClientById(clientId)
                .map(ClientDTO::new);
    }

    public Optional<Client> getClientByUsername(String username) {
        return clientRepository.getClientByUsername(username);
    }

    Collection<ClientDTO> getAllClients() {
        return clientRepository.findAll().stream()
                .map(ClientDTO::new)
                .collect(Collectors.toList());
    }

    Client createClient(ClientDTO clientDTO) {
        return clientRepository.save(new Client(clientDTO));
    }

    @Transactional
    public void deleteClient(String username) {
        clientRepository.deleteByUsername(username);
    }

    Collection<AccountDTO> transformClientAccounts(Client client) {
        return client.getAccounts().stream()
                .map(AccountDTO::new)
                .collect(Collectors.toList());
    }

}
