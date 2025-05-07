package com.fiap.config;

import com.fiap.MainApplication;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;

@CucumberContextConfiguration
@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
		classes = MainApplication.class
)
@Testcontainers
public class CucumberSpringConfiguration extends PostgresTestContainer {
}