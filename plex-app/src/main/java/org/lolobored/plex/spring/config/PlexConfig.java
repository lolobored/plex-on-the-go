package org.lolobored.plex.spring.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


@Configuration
@PropertySource("classpath:application.properties")
@ConfigurationProperties(prefix = "plex")
@Data
public class PlexConfig {

	private String url;
}
