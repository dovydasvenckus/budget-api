package com.dovydasvenckus.budget.account;

import com.dovydasvenckus.budget.client.Client;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "account_id")
    private Long id;

    @NotBlank
    @Column(name = "name")
    private String name;

    @Enumerated(STRING)
    @Column(name = "type", nullable = false)
    private AccountType type;

    @ManyToOne
    private Client client;

    Account(String name, AccountType type) {
        this.name = name;
        this.type = type;
    }

    Account(AccountDTO accountDTO) {
        this.name = accountDTO.getName();
        this.type = accountDTO.getType();
    }

    Account() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
