package com.dovydasvenckus.budget.acceptance;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

public class CreateAccountRequest {
    @NotBlank
    private String name;

    @NotNull
    private String type;

    public CreateAccountRequest(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
