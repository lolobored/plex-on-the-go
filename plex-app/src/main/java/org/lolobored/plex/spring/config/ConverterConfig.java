package org.lolobored.plex.spring.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:converter.properties")
@ConfigurationProperties(prefix = "converter")
@Data
public class ConverterConfig {

	private String ffmpeg;
	private String ffprobe;
	private String tempDirectory;
}
