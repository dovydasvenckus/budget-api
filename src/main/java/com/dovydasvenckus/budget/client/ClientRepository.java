package com.dovydasvenckus.budget.client;

import org.jdbi.v3.core.Jdbi;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ClientRepository {

    private final Jdbi jdbi;

    public ClientRepository(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    Collection<Client> findAll() {
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT * FROM clients")
                        .mapToBean(Client.class)
                        .list()
        );
    }

    void save(Client client) {
        jdbi.withHandle(handle -> handle
                .createUpdate(
                        "INSERT INTO clients (id, username, first_name, last_name) "
                                + " VALUES (:id, :username, :firstName, :lastName)"
                )
                .bind("id", client.getId())
                .bind("username", client.getUsername())
                .bind("firstName", client.getFirstName())
                .bind("lastName", client.getLastName())
                .execute()
        );
    }

    Optional<Client> getUser(String username) {
        return jdbi.withHandle(handle -> handle
        .createQuery("SELECT * FROM clients WHERE username = :username")
                .bind("username", username)
                .mapToBean(Client.class)
                .findOne()
        );
    }

    Optional<Client> getClientById(UUID id) {
        return jdbi.withHandle(handle -> handle
                .createQuery("SELECT * FROM clients WHERE id = :id")
                .bind("id", id)
                .mapToBean(Client.class)
                .findOne()
        );
    }
}
