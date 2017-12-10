package com.dovydasvenckus.budget.client

import com.dovydasvenckus.budget.account.AccountDTO
import com.dovydasvenckus.budget.account.AccountType
import com.dovydasvenckus.budget.integration.John
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import spock.lang.Specification

import static com.dovydasvenckus.budget.ResourceMapping.CLIENT_RESOURCE
import static com.dovydasvenckus.budget.account.AccountType.EXPENSE
import static com.dovydasvenckus.budget.account.AccountType.INCOME

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ClientResourceSpec extends Specification {

    @Autowired
    private John john

    def cleanup() {
        john.cleanUp()
    }

    def 'should register client'() {
        when:
            ResponseEntity<Void> response = john.register()

        then:
            response.statusCode == HttpStatus.CREATED
            response.headers.getLocation().path.startsWith(CLIENT_RESOURCE)
    }

    def 'should be able to retrieve registered user'() {
        given:
            URI clientURI = john.register().headers.getLocation()

        when:
            ResponseEntity<ClientDTO> clientResponse = john.getClientInfo(clientURI)

        then:
            clientResponse.statusCode == HttpStatus.OK

            with(clientResponse.body) {
                id != null
                username == john.username
                firstName == john.firstName
                lastName == john.lastName
            }
    }

    def 'client should be able to open new account'() {
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

    def 'client should be able to open two new accounts'() {
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
