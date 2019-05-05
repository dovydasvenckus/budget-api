package com.dovydasvenckus.budget.account;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends CrudRepository<Account, Long> {

    List<Account> findAll();

    void deleteAllByClientUsername(String username);
}
