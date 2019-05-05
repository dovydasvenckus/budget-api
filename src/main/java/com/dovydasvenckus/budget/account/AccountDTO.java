package com.dovydasvenckus.budget.account;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class AccountDTO {

    private Long id;

    @NotBlank
    private String name;

    @NotNull
    private AccountType type;

    AccountDTO() {
    }

    public AccountDTO(Account account) {
        this.id = account.getId();
        this.name = account.getName();
        this.type = account.getType();
    }

    public AccountDTO(String name, AccountType type) {
        this.name = name;
        this.type = type;
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
