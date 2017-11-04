package com.dovydasvenckus.budget.client;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Client {

    @Id
    @Column(name = "client_id")
    private Long id;

    @Column(nullable = false, name = "username")
    private String username;

    public Client() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
