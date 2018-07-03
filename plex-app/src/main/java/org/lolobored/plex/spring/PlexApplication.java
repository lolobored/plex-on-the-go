package org.lolobored.plex.spring;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.lolobored.plex.spring.converter.ConversionJob;
import org.lolobored.plex.spring.converter.ConversionProgress;
import org.lolobored.plex.spring.converter.ConverterQueue;
import org.lolobored.plex.spring.models.PendingConversion;
import org.lolobored.plex.spring.repository.ConversionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchAutoConfiguration;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchDataAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.List;

@SpringBootApplication(exclude = {ElasticsearchAutoConfiguration.class, ElasticsearchDataAutoConfiguration.class})
@ComponentScan(basePackages = {"org.lolobored"})
@EnableElasticsearchRepositories("org.lolobored")
@EnableScheduling
public class PlexApplication {

	@Bean
	@Primary
	public ObjectMapper serializingObjectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		JavaTimeModule javaTimeModule = new JavaTimeModule();
		objectMapper.registerModule(javaTimeModule);
		return objectMapper;
	}

	@Autowired
	ConversionRepository conversionRepository;

	@Bean(name = "conversionQueue")
	public ConverterQueue getConversionQueue() {
		List<PendingConversion> listOfMedia = conversionRepository.findAllByDoneFalseOrderByCreationDateTime();

		ConverterQueue converterQueue = new ConverterQueue();
		// load the pendingConversion queue
		for (PendingConversion pendingConversion : listOfMedia) {
			ConversionJob conversionJob = new ConversionJob();
			conversionJob.setPendingConversion(pendingConversion);
			conversionJob.setConversionProgress(new ConversionProgress());
			converterQueue.addJob(conversionJob);
		}

		return converterQueue;
	}

	@Bean
	public ElasticsearchOperations elasticsearchTemplate() throws Exception {
		return new ElasticsearchTemplate(client());
	}

	public static void main(String[] args) {
		SpringApplication.run(PlexApplication.class, args);
	}
}
