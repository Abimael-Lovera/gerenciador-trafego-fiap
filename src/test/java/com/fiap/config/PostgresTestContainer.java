package com.fiap.config;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

public class PostgresTestContainer {

	private static final String IMAGE_VERSION = "postgres:15";

	@Container
	static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(IMAGE_VERSION)
			.withDatabaseName("testdb")
			.withUsername("test")
			.withPassword("test")
			.withReuse(true);

	static {
		// Iniciar o contêiner de forma explícita
		if (!postgres.isRunning()) {
			postgres.start();
			System.out.println("⚡ PostgreSQL container started at: " + postgres.getJdbcUrl());
		}
	}

	@DynamicPropertySource
	static void databaseProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", postgres::getJdbcUrl);
		registry.add("spring.datasource.username", postgres::getUsername);
		registry.add("spring.datasource.password", postgres::getPassword);
		registry.add("spring.datasource.driver-class-name", postgres::getDriverClassName);
		registry.add("spring.jpa.hibernate.ddl-auto", () -> "create");
		registry.add("spring.flyway.enabled", () -> "true");
		registry.add("spring.flyway.locations", () -> "classpath:db/migration/postgres");
	}
}