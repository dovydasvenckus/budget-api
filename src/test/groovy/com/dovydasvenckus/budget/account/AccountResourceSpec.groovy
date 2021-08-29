package com.dovydasvenckus.budget.account

import com.dovydasvenckus.budget.config.ResourceSpecification

import static com.dovydasvenckus.budget.account.AccountType.EXPENSE
import static com.dovydasvenckus.budget.account.AccountType.INCOME

class AccountResourceSpec extends ResourceSpecification {

    def 'user should be able to open new account'() {
        given:
            john.register()

        when:
            john.openAccount("Rent", EXPENSE)

        then:
            with(john.getLastAccount()) {
                id != null
                name == 'Rent'
                type == EXPENSE
            }
    }

    def 'user should be able to open two new accounts'() {
        given:
            john.register()

        when:
            john.openAccount("Salary", INCOME)
            john.openAccount("Rent", EXPENSE)

        then:
            List<AccountDTO> accounts = john.getAccounts()

            verifyAccount(accounts[0], "Salary", INCOME)
            verifyAccount(accounts[1], "Rent", EXPENSE)

    }

    private static void verifyAccount(AccountDTO accountDto, String name, AccountType type) {
        assert accountDto.id != null
        assert accountDto.name == name
        assert accountDto.type == type
    }
}
