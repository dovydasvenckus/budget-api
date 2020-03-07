package com.dovydasvenckus.budget.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    Optional<ClientDTO> getClientById(UUID clientId) {
        return clientRepository
                .getClientById(clientId)
                .map(ClientDTO::new);
    }

    public Optional<Client> getClientByUsername(String username) {
        return clientRepository.getUser(username);
    }

    Collection<ClientDTO> getAllClients() {
        return clientRepository.findAll().stream()
                .map(ClientDTO::new)
                .collect(Collectors.toList());
    }

    Client createClient(ClientDTO clientDTO) {
        Client client = new Client(clientDTO);
        clientRepository.save(new Client(clientDTO));

        return client;
    }
}
