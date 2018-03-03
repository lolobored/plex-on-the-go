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

/**
 * Helper API class to interact with Elastic Search
 * It provides the different methods to work with Elastic Search at a high-level
 */
public class ElasticSearch {

	private static Logger logger = Logger.getLogger(ElasticSearch.class.getName());

	private static ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule())
			.setSerializationInclusion(JsonInclude.Include.NON_NULL);

	/**
	 * Insert any Media object into Elastic Search
	 * @param elasticSearchUrl the base url for Elastic search (ie http://localhost:9200)
	 * @param media the Media object which will be inserted into Elastic Search
	 * @param bypassSSL determine whether or not we have to bypassSSL checks (for self-generated SSL certificates)
	 * @throws HttpException in case of an error while calling the REST API from elastic search
	 * @throws JsonProcessingException in case of an error while serializing / deserializing objects using Jackson
	 */
	public static void insertMedia(String elasticSearchUrl, Media media, boolean bypassSSL) throws HttpException, JsonProcessingException {

		// Transform the media object into a JSON request using Jackson
		String request = mapper.writeValueAsString(media);

		// Logging action
		logger.fine(String.format("Request [%s] will be sent to [%s/media/_doc] bypassing ssl checks [%s]", request, elasticSearchUrl, bypassSSL));

		// getting back the response using the HttpUtil utility
		// TODO: 03/03/2018 Would be good to check the response
		String response = HttpUtil.getInstance(bypassSSL).post(String.format("%s/media/_doc", elasticSearchUrl), request, getElasticSearchHeaders());

		// Logging result
		logger.fine(String.format("Response received from elasticsearch [%s]", response));

	}

	/**
	 * Deletes every media stored into Elastic Search for a specific user
	 * @param elasticSearchUrl the base url for Elastic search (ie http://localhost:9200)
	 * @param username the user to which the media belong to
	 * @param bypassSSL determine whether or not we have to bypassSSL checks (for self-generated SSL certificates)
	 * @throws HttpException in case of an error while calling the REST API from elastic search
	 * @throws JsonProcessingException in case of an error while serializing / deserializing objects using Jackson
	 */
	public static void deleteMedia(String elasticSearchUrl, String username, boolean bypassSSL) throws HttpException, JsonProcessingException {
		try {
			// Define a search for username
			Search search = buildSearch(0,0);
			Filters filters = new Filters();
			filters.addUser(username);
			search.getQuery().getBool().setMust(filters.getFiltersAsMust());

			// build request using Jackson
			String request = mapper.writeValueAsString(search);

			// Logging action
			logger.fine(String.format("Search request [%s] will be sent to [%s/media/_delete_by_query] bypassing ssl checks [%s]", request, elasticSearchUrl, bypassSSL));

			// getting back the response using the HttpUtil utility
			// TODO: 03/03/2018 Would be good to check the response
			String response = HttpUtil.getInstance(bypassSSL).post(String.format("%s/media/_delete_by_query", elasticSearchUrl), request, getElasticSearchHeaders());

			// Logging result
			logger.fine(String.format("Response received from elasticsearch [%s]", response));
		}
		catch (HttpException err){
			logger.info("Exception occured while attempting to delete: "+err.getResponse());
		}

	}

	/**
	 * Retrieve a list of movies for a specific user
	 * @param elasticSearchUrl the base url for Elastic search (ie http://localhost:9200)
	 * @param user the user to which the media belong to
	 * @param size the maximum number of result which will be retrieved
	 * @param from the starting index
	 * @param bypassSSL determine whether or not we have to bypassSSL checks (for self-generated SSL certificates)
	 * @return the List of Media
	 * @throws IOException in case of an error while calling Jackson
	 * @throws HttpException in case of an error while calling the REST API from elastic search
	 */
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
		// Sets the final query
		search.getQuery().getBool().setMust(filter.getFiltersAsMust());

		// Transform the query into JSON using Jackson
		String request = mapper.writeValueAsString(search);

		// Log the action
		logger.fine(String.format("Search request [%s] will be sent to [%s/media/_search] bypassing ssl checks [%s]", request, elasticSearchUrl, bypassSSL));

		// Retrieving the responses from Elastic Search
		String response = HttpUtil.getInstance(bypassSSL).post(String.format("%s/media/_search", elasticSearchUrl), request, getElasticSearchHeaders());

		// Logging the response
		logger.fine(String.format("Response received from elasticsearch [%s]", response));

		// build a Result object out of the JSON response using Jackson
		// That result object will have all the Media which were fetched
		Result result = mapper.readValue(response, Result.class);
		// feed the list
		for (Source source: result.getHits().getList()){
			media.add(source.getMedia());
		}
		return media;
	}

	/**
	 * Retrieve the common HTTP header for Elastic Search using a Map object
	 * That Map object will be used in HttpUtils
	 * @return
	 */
	private static Map<String, String> getElasticSearchHeaders() {
		Map<String, String> headers = new HashMap<>();
		headers.put("Content-Type", "application/json;charset=UTF-8");
		return headers;
	}

	/**
	 * Build the common search objects
	 * @param size the maximum number of retrieved objects
	 * @param from the starting index for the objects
	 * @return
	 */
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
