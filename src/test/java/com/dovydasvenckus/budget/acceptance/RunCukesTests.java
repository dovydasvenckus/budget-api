package com.dovydasvenckus.budget.acceptance;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        glue = {"com.dovydasvenckus.budget.acceptance"},
        features = {"src/test/resources/features"},
        plugin = {"pretty", "html:build/cucumber"}
        )
public class RunCukesTests {
}
