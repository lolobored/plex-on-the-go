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
import org.lolobored.plex.elasticsearch.repository.MediaRepository;
import org.lolobored.plex.elasticsearch.result.Result;
import org.lolobored.plex.elasticsearch.result.Source;
import org.lolobored.plex.model.Media;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ElasticSearchServiceImpl implements ElasticSearchService{


    @Autowired
    private MediaRepository repository;

    private static Logger logger = LoggerFactory.getLogger(ElasticSearchServiceImpl.class);

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
    @Override
    public void insertMedia(Media media, String elasticSearchUrl, boolean bypassSSL) throws IOException, HttpException {
        // Transform the media object into a JSON request using Jackson
        String request = mapper.writeValueAsString(media);

        // Logging action
        logger.debug(String.format("Request [%s] will be sent to [%s/media/_doc] bypassing ssl checks [%s]", request, elasticSearchUrl, bypassSSL));

        // Search for media first
        Media existingMedia = repository.findByUserAndPlexId(media.getUser(), media.getPlexId());

        if (existingMedia != null) {
            media.setId(existingMedia.getId());
        }
        repository.save(media);

    }

    @Override
    public List<String> getMoviesGenre() {
        return null;
    }

    @Override
    public List<Integer> getAllYears() {
        return null;
    }

    @Override
    public List<Media> getAllMovies() {
        return null;
    }

    @Override
    public List<Media> searchMovies(List<String> genre) {
        return null;
    }

    public static Media getMoviePerUrl(String elasticSearchUrl, String user, String fileUrl, int size, int from, boolean bypassSSL) throws IOException, HttpException {
        List<Media> media = new ArrayList<>();
        Filters filter = new Filters();
        // search per user and only on movies
        filter.addUser(user);
        filter.addTypeMovie();
        filter.addFileLocation(fileUrl);
        Search search = buildSearch(size, from);
        // Sets the final query
        search.getQuery().getBool().setMust(filter.getFiltersAsMust());

        // Transform the query into JSON using Jackson
        String request = mapper.writeValueAsString(search);

        // Log the action
        logger.debug(String.format("Search request [%s] will be sent to [%s/media/_search] bypassing ssl checks [%s]", request, elasticSearchUrl, bypassSSL));

        // Retrieving the responses from Elastic Search
        String response = HttpUtil.getInstance(bypassSSL).post(String.format("%s/media/_search", elasticSearchUrl), request, getElasticSearchHeaders());

        // Logging the response
        logger.debug(String.format("Response received from elasticsearch [%s]", response));

        // build a Result object out of the JSON response using Jackson
        // That result object will have all the Media which were fetched
        Result result = mapper.readValue(response, Result.class);
        // feed the list
        for (Source source: result.getHits().getList()){
            return source.getMedia();
        }
        return null;
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
    private static Search buildSearch(Integer size, Integer from){
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
