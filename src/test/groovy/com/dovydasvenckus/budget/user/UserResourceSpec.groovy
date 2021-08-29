package com.dovydasvenckus.budget.user

import com.dovydasvenckus.budget.config.ResourceSpecification
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

import static com.dovydasvenckus.budget.ResourceMapping.USER_RESOURCE

class UserResourceSpec extends ResourceSpecification {

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
}
