package com.dovydasvenckus.budget.account;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

public class Account {

    private UUID id;

    @NotBlank
    private String name;

    private AccountType type;

    private UUID userId;

    Account(AccountDTO accountDTO) {
        this.id = UUID.randomUUID();
        this.name = accountDTO.getName();
        this.type = accountDTO.getType();
        this.userId = accountDTO.getUserId();
    }

    public Account() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
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


    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

}
