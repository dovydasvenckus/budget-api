package com.dovydasvenckus.budget.client;

import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.Optional;

interface ClientRepository extends CrudRepository<Client, Long> {

    Collection<Client> findAll();

    Optional<Client> getClientByUsername(String username);

    Optional<Client> getClientById(long id);

    void deleteByUsername(String username);
}
