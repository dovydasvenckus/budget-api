package com.dovydasvenckus.budget.acceptance;

import com.dovydasvenckus.budget.BudgetApplication;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;

import java.io.IOException;

@ContextConfiguration
@SpringBootTest(
        classes = BudgetApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
abstract class CucumberIntegrationTest {

    @Autowired
    private Environment environment;

    static {
        Unirest.setObjectMapper(new ObjectMapper() {
            private com.fasterxml.jackson.databind.ObjectMapper jacksonObjectMapper
                    = new com.fasterxml.jackson.databind.ObjectMapper();

            public <T> T readValue(String value, Class<T> valueType) {
                try {
                    return jacksonObjectMapper.readValue(value, valueType);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            public String writeValue(Object value) {
                try {
                    return jacksonObjectMapper.writeValueAsString(value);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    int getLocalPort() {
        return new Integer(environment.getProperty("local.server.port"));
    }

    String getApiAddress() {
        return String.format("http://localhost:%d/api", getLocalPort());
    }
}
