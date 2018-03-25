package org.lolobored.plex.elasticsearch;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.lolobored.http.HttpException;
import org.lolobored.plex.elasticsearch.repository.MediaRepository;
import org.lolobored.plex.elasticsearch.search.MovieGenres;
import org.lolobored.plex.elasticsearch.search.MovieYears;
import org.lolobored.plex.model.Media;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ElasticSearchServiceImpl implements ElasticSearchService {


	@Autowired
	private MediaRepository repository;

	private static Logger logger = LoggerFactory.getLogger(ElasticSearchServiceImpl.class);

	private static ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule())
		.setSerializationInclusion(JsonInclude.Include.NON_NULL);


	/**
	 * Insert any Media object into Elastic Search
	 *
	 * @param media            the Media object which will be inserted into Elastic Search
	 * @throws HttpException           in case of an error while calling the REST API from elastic search
	 * @throws JsonProcessingException in case of an error while serializing / deserializing objects using Jackson
	 */
	@Override
	public void insertMedia(Media media) throws IOException, HttpException {
		// Search for media first
		Media existingMedia = repository.findByUserAndPlexId(media.getUser(), media.getPlexId());

		if (existingMedia != null) {
			media.setId(existingMedia.getId());
		} else {
			media.setId(UUID.randomUUID().toString());
		}

		repository.save(media);
		MovieGenres.getInstance().addGenres(media.getGenres());
		MovieYears.getInstance().addYear(media.getYear());
	}

	@Override
	public List<String> getMoviesGenre() {
		return MovieGenres.getInstance().getGenres();
	}

	@Override
	public List<Integer> getAllYears() {
		return MovieYears.getInstance().getYears();
	}

	@Override
	public List<Media> getAllMovies(String user) {
		List<Media> result= new ArrayList();
		Pageable pageRequest= new PageRequest(0, 1000, Sort.Direction.ASC, "title.keyword");
		Page<Media> page= repository.findByUser(user,pageRequest);
		result.addAll(page.getContent()) ;
		for (int i=1; i< page.getTotalPages(); i++){
			pageRequest = pageRequest.next();
			page= repository.findByUser(user,pageRequest);
			result.addAll(page.getContent()) ;
		}
		return result;

	}

	@Override
	public List<Media> searchMovies(String user, List<String> genre, Integer startYear, Integer endYear) {
		List<Media> result= new ArrayList<>();
		// if genre is empty
		if (genre.isEmpty()){
			return result;
		}
		Pageable pageRequest= new PageRequest(0, 1000, Sort.Direction.ASC, "title.keyword");
		Page<Media> page= repository.findByUserAndGenresInAndYearGreaterThanEqualAndYearLessThanEqual(user, genre, startYear, endYear, pageRequest);
		result.addAll(page.getContent()) ;
		for (int i=1; i< page.getTotalPages(); i++){
			pageRequest = pageRequest.next();
			page= repository.findByUserAndGenresInAndYearGreaterThanEqualAndYearLessThanEqual(user, genre, startYear, endYear, pageRequest);
			result.addAll(page.getContent()) ;
		}
		return result;
	}


}
