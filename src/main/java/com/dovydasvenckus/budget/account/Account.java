package com.dovydasvenckus.budget.account;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.Collection;

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
    private AccountType accountType;

    @ManyToOne
    private Account parent;

    @OneToMany(mappedBy = "parent")
    private Collection<Account> subAccounts;

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

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public Account getParent() {
        return parent;
    }

    public void setParent(Account parent) {
        this.parent = parent;
    }

    public Collection<Account> getSubAccounts() {
        return subAccounts;
    }

    public void setSubAccounts(Collection<Account> subAccounts) {
        this.subAccounts = subAccounts;
    }
}
