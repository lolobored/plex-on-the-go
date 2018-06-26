package org.lolobored.plex.spring.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;

import java.io.IOException;
import java.util.Map;


@Configuration
@PropertySource(value = {"classpath:keycloak.json"}, factory=KeycloakConfig.JsonLoader.class )
@Data
public class KeycloakConfig {

	@Autowired
	Environment env;

	@Bean
	public String getRealm() {
		return env.getProperty("realm");
	}
	@Bean
	public String getResource() {
		return env.getProperty("resource");
	}
	@Bean
	public String getAuthServerUrl() {
		return env.getProperty("auth-server-url");
	}

	public static class JsonLoader implements PropertySourceFactory {

		@Override
		public org.springframework.core.env.PropertySource<?> createPropertySource(String name,
																																							 EncodedResource resource) throws IOException {
			Map readValue = new ObjectMapper().readValue(resource.getInputStream(), Map.class);
			return new MapPropertySource("json-source", readValue);
		}

	}
}
