package org.lolobored.plex.elasticsearch;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.lolobored.http.HttpException;
import org.lolobored.http.HttpUtil;
import org.lolobored.plex.elasticsearch.filters.Filters;
import org.lolobored.plex.elasticsearch.query.Bool;
import org.lolobored.plex.elasticsearch.query.Query;
import org.lolobored.plex.elasticsearch.query.Search;
import org.lolobored.plex.elasticsearch.query.Sort;
import org.lolobored.plex.elasticsearch.result.Result;
import org.lolobored.plex.elasticsearch.result.Source;
import org.lolobored.plex.model.Media;

import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

public class ElasticSearch {

	private static Logger logger = Logger.getLogger(ElasticSearch.class.getName());

	private static ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule())
			.setSerializationInclusion(JsonInclude.Include.NON_NULL);

	public static void insertMedia(String elasticSearchUrl, Media media, boolean bypassSSL) throws HttpException, JsonProcessingException {

		String request = mapper.writeValueAsString(media);
		logger.fine(String.format("Request [%s]sent to [%s/media/_doc] bypassing ssl checks [%s]", request, elasticSearchUrl, bypassSSL));
		String response = HttpUtil.getInstance(bypassSSL).post(String.format("%s/media/_doc", elasticSearchUrl), request, getElasticSearchHeaders());
		logger.fine(String.format("Response received from elasticsearch [%s]", response));

	}

	public static void deleteMedia(String elasticSearchUrl, Search search, boolean bypassSSL) throws HttpException, JsonProcessingException {
		try {
			String request = mapper.writeValueAsString(search);
			logger.fine(String.format("Search request [%s]sent to [%s/media/_delete_by_query] bypassing ssl checks [%s]", request, elasticSearchUrl, bypassSSL));
			String response = HttpUtil.getInstance(bypassSSL).post(String.format("%s/media/_delete_by_query", elasticSearchUrl), request, getElasticSearchHeaders());
			logger.fine(String.format("Response received from elasticsearch [%s]", response));
		}
		catch (HttpException err){
			logger.info("Exception occured while attempting to delete: "+err.getResponse());
		}

	}

	public static List<Media> getMoviesPerUser(String elasticSearchUrl, String user, int size, int from, boolean bypassSSL) throws IOException, HttpException {
		List<Media> media = new ArrayList<>();
		Filters filter = new Filters();
		// search per user and only on movies
		filter.addUser(user);
		filter.addTypeMovie();
		Search search = buildSearch(size, from);
		// sort by title
		Sort sort = new Sort();
		sort.setTitleSort();
		search.setSort(Arrays.asList(sort));

		// query
		search.getQuery().getBool().setMust(filter.getFiltersAsMust());

		String request = mapper.writeValueAsString(search);
		logger.fine(String.format("Search request [%s] sent to [%s/media/_search] bypassing ssl checks [%s]", request, elasticSearchUrl, bypassSSL));
		String response = HttpUtil.getInstance(bypassSSL).post(String.format("%s/media/_search", elasticSearchUrl), request, getElasticSearchHeaders());
		logger.fine(String.format("Response received from elasticsearch [%s]", response));
		Result result = mapper.readValue(response, Result.class);
		for (Source source: result.getHits().getList()){
			media.add(source.getMedia());
		}
		return media;
	}

	private static void getListOfMoviesPerGenre(String elasticSearchUrl, Search search, boolean bypassSSL, int total, int from) throws HttpException, JsonProcessingException {

		String request = mapper.writeValueAsString(search);
		logger.fine(String.format("Search request [%s] sent to [%s/media/_delete_by_query] bypassing ssl checks [%s]", request, elasticSearchUrl, bypassSSL));
		String response = HttpUtil.getInstance(bypassSSL).post(String.format("%s/media/_delete_by_query", elasticSearchUrl), request, getElasticSearchHeaders());
		logger.fine(String.format("Response received from elasticsearch [%s]", response));

	}

	private static Map<String, String> getElasticSearchHeaders() {
		Map<String, String> headers = new HashMap<>();
		headers.put("Content-Type", "application/json;charset=UTF-8");
		return headers;
	}

	private static Search buildSearch(int size, int from){
		Search search = new Search();
		search.setFrom(from);
		search.setSize(size);
		Query query = new Query();
		search.setQuery(query);
		Bool bool = new Bool();
		query.setBool(bool);
		return search;
	}

}
