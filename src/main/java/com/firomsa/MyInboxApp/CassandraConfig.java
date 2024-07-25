package com.firomsa.MyInboxApp;

import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.cassandra.CqlSessionBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@Configuration
@EnableCassandraRepositories
public class CassandraConfig {

    @Value("${secure-connect-bundle}")
    private String secureConnectBundlePath;

    @Bean
	public CqlSessionBuilderCustomizer sessionBuilderCustomizer() {
		return builder -> builder.withCloudSecureConnectBundle(Paths.get(secureConnectBundlePath));
	}
}
