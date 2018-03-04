package org.lolobored.plex.spring.controllers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.lolobored.http.HttpException;
import org.lolobored.plex.PlexApis;
import org.lolobored.plex.elasticsearch.ElasticSearch;
import org.lolobored.plex.model.Media;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class MoviesController {

    @GetMapping("/movies")
    public void getMedia() throws IOException, HttpException {

    }
}
