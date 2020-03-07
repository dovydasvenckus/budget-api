package com.dovydasvenckus.budget.client;


import javax.validation.constraints.NotBlank;
import java.util.UUID;

public class ClientDTO {

    private UUID id;

    @NotBlank
    private String username;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    public ClientDTO() {
    }

    public ClientDTO(String username, String firstName, String lastName) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public ClientDTO(Client client) {
        this.id = client.getId();
        this.username = client.getUsername();
        this.firstName = client.getFirstName();
        this.lastName = client.getLastName();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
