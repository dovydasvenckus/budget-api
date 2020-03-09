package com.dovydasvenckus.budget.user;

import org.jdbi.v3.core.Jdbi;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Repository
public class UserRepository {

    private final Jdbi jdbi;

    public UserRepository(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    Collection<User> findAll() {
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT * FROM users")
                        .mapToBean(User.class)
                        .list()
        );
    }

    void save(User user) {
        jdbi.withHandle(handle -> handle
                .createUpdate(
                        "INSERT INTO users (id, username, first_name, last_name) "
                                + " VALUES (:id, :username, :firstName, :lastName)"
                )
                .bind("id", user.getId())
                .bind("username", user.getUsername())
                .bind("firstName", user.getFirstName())
                .bind("lastName", user.getLastName())
                .execute()
        );
    }

    Optional<User> getUser(String username) {
        return jdbi.withHandle(handle -> handle
        .createQuery("SELECT * FROM users WHERE username = :username")
                .bind("username", username)
                .mapToBean(User.class)
                .findOne()
        );
    }

    Optional<User> getUserById(UUID id) {
        return jdbi.withHandle(handle -> handle
                .createQuery("SELECT * FROM users WHERE id = :id")
                .bind("id", id)
                .mapToBean(User.class)
                .findOne()
        );
    }
}
