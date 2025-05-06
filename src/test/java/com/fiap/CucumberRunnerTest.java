package com.fiap;

import org.junit.runner.RunWith;

import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/features",
    glue = {"com.fiap.steps", "com.fiap.config"},
    plugin = {"pretty", "html:target/cucumber-reports.html"},
    snippets = CucumberOptions.SnippetType.CAMELCASE
)
public class CucumberRunnerTest {
    
}
