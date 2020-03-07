package com.dovydasvenckus.budget.account;

import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccountRepository {

    @SqlQuery("SELECT * FROM accounts")
    @RegisterBeanMapper(Account.class)
    List<Account> findAll();

    @SqlQuery("SELECT a.id, a.name, a.type FROM accounts as a "
            + "INNER JOIN clients c ON c.id = a.client_id "
            + "WHERE c.username = ?")
    @RegisterBeanMapper(Account.class)
    List<Account> findAllByUsername(@Bind String username);

    @SqlQuery("SELECT * FROM accounts WHERE id = ?")
    @RegisterBeanMapper(Account.class)
    Optional<Account> findById(@Bind UUID id);

    @SqlUpdate("INSERT INTO accounts (id, name, type, client_id)"
            + " VALUES (:id, :name, :type, :clientId)")
    void save(@BindBean Account account);
}
