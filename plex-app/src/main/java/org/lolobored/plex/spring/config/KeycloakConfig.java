package org.lolobored.plex.spring.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


@Configuration
@PropertySource("classpath:keycloak.json")
@Data
public class KeycloakConfig {

	private String realm;
	private String resource;
	private String authServerUrl;
}
