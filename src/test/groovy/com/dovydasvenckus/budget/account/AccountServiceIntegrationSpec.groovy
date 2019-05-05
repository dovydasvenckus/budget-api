package com.dovydasvenckus.budget.account

import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import static com.dovydasvenckus.budget.account.AccountType.INCOME

@SpringBootTest
class AccountServiceIntegrationSpec extends Specification {

    @SpringBean
    AccountRepository accountRepository = Stub()

    @Autowired
    AccountService accountService

    def 'should save new account'() {
        given:
            AccountDTO account = new AccountDTO(name: "Income", type: INCOME)
            Account accountWithId = new Account(id: 5, name: "Income", type: INCOME)
            accountRepository.save(_ as Account) >> accountWithId

        when:
            AccountDTO result = accountService.createAccount(account)

        then:
            with(result) {
                id == 5
                name == 'Income'
                type == INCOME
            }
    }
}
