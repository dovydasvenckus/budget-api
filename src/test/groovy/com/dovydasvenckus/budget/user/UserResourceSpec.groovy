package com.dovydasvenckus.budget.user

import com.dovydasvenckus.budget.account.AccountDTO
import com.dovydasvenckus.budget.account.AccountType
import com.dovydasvenckus.budget.config.TestDatabaseConfig
import com.dovydasvenckus.budget.integration.John
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import spock.lang.Specification

import static com.dovydasvenckus.budget.ResourceMapping.USER_RESOURCE
import static com.dovydasvenckus.budget.account.AccountType.EXPENSE
import static com.dovydasvenckus.budget.account.AccountType.INCOME

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TestDatabaseConfig)
class UserResourceSpec extends Specification {

    @Autowired
    private John john

    def cleanup() {
        john.cleanUp()
    }

    def 'should register user'() {
        when:
            ResponseEntity<Void> response = john.register()

        then:
            response.statusCode == HttpStatus.CREATED
            response.headers.getLocation().path.startsWith(USER_RESOURCE)
    }

    def 'should be able to retrieve registered user'() {
        given:
            URI userUri = john.register().headers.getLocation()

        when:
            ResponseEntity<UserDTO> userResponse = john.getUserInfo(userUri)

        then:
            userResponse.statusCode == HttpStatus.OK

            with(userResponse.body) {
                id != null
                username == john.username
                firstName == john.firstName
                lastName == john.lastName
            }
    }

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
