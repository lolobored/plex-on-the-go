package org.lolobored.plex.elasticsearch;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.lolobored.http.HttpException;
import org.lolobored.http.HttpUtil;
import org.lolobored.plex.model.Episode;
import org.lolobored.plex.model.Movie;

import java.util.HashMap;
import java.util.Map;

public class ElasticSearch {

    private static ObjectMapper mapper = new ObjectMapper();

    public static void insertMovie(String elasticSearchUrl, Movie media, boolean bypassSSL) throws HttpException, JsonProcessingException {

        String request = mapper.writeValueAsString(media);
        String response = HttpUtil.getInstance(bypassSSL).post(String.format("%s/media/_doc", elasticSearchUrl), request, getElasticSearchHeaders());

    }
    public static void insertEpisode(String elasticSearchUrl, Episode media, boolean bypassSSL) throws JsonProcessingException, HttpException {
        String request = mapper.writeValueAsString(media);
        String response = HttpUtil.getInstance(bypassSSL).post(String.format("%s/media/_doc", elasticSearchUrl), request, getElasticSearchHeaders());

    }

    private static Map<String, String> getElasticSearchHeaders(){
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json;charset=UTF-8");
        return headers;
    }

}
