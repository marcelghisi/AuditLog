package com.jumia.audit.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class AuditLogsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuditLogsApplication.class, args);
	}
}
