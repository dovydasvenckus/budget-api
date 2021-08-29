package com.dovydasvenckus.budget.config

import com.dovydasvenckus.budget.integration.John
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TestDatabaseConfig)
abstract class ResourceSpecification extends Specification {

    @Autowired
    protected John john

    def cleanup() {
        john.cleanUp()
    }

}
