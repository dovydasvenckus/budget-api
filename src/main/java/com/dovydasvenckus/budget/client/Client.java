package com.dovydasvenckus.budget.client;

import com.dovydasvenckus.budget.account.Account;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;

import java.util.Collection;
import java.util.LinkedList;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "client_id")
    private Long id;

    @NotBlank
    @Column(nullable = false, name = "username", unique = true)
    private String username;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @OneToMany(mappedBy = "client")
    private Collection<Account> accounts;

    public Client() {
    }

    public Client(ClientDTO clientDTO) {
        this.username = clientDTO.getUsername();
        this.firstName = clientDTO.getFirstName();
        this.lastName = clientDTO.getLastName();
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

    public Collection<Account> getAccounts() {
        if (accounts == null) {
            accounts = new LinkedList<>();
        }

        return accounts;
    }

    public void addAccount(Account account) {
        if (accounts == null) {
            accounts = new LinkedList<>();
        }

        accounts.add(account);
    }

    public void setAccounts(Collection<Account> accounts) {
        this.accounts = accounts;
    }
}
