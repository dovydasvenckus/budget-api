package com.dovydasvenckus.budget.account;

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

    Account(String name, AccountType type) {
        this.name = name;
        this.type = type;
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
}
