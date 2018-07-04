package org.lolobored.plex.elasticsearch.config;

import lombok.Data;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

import java.net.InetAddress;

@Configuration
@Data
@PropertySource(value = "classpath:application.properties")
public class ElasticSearchConfig {

	@Value("${elasticsearch.host}")
	private String EsHost;

	@Value("${elasticsearch.port}")
	private int EsPort;

	@Value("${spring.data.jest.uri}")
	private String ESHttpUrl;

	@Value("${elasticsearch.maxresults}")
	private Integer ESMaxResult;

	@Bean
	public Client client() throws Exception {

		Settings esSettings = Settings.builder()
			.put("client.transport.sniff", false)
			.build();


		TransportClient client = new PreBuiltTransportClient(esSettings)
			.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(EsHost), EsPort));
		return client;
	}

	@Bean
	public ElasticsearchTemplate elasticsearchTemplate() throws Exception {

		return new ElasticsearchTemplate(client());
	}
}
