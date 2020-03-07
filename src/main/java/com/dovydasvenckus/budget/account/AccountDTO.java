package com.dovydasvenckus.budget.account;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

public class AccountDTO {

    private UUID id;

    @NotBlank
    private String name;

    @NotNull
    private AccountType type;

    private UUID clientId;

    AccountDTO() {
    }

    public AccountDTO(Account account) {
        this.id = account.getId();
        this.name = account.getName();
        this.type = account.getType();
        this.clientId = account.getClientId();
    }

    public AccountDTO(String name, AccountType type) {
        this.name = name;
        this.type = type;
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

    public UUID getClientId() {
        return clientId;
    }

    public void setClientId(UUID clientId) {
        this.clientId = clientId;
    }
}
