package org.lolobored.plex.elasticsearch;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.lolobored.http.HttpException;
import org.lolobored.http.HttpUtil;
import org.lolobored.plex.elasticsearch.config.ElasticSearchConfig;
import org.lolobored.plex.elasticsearch.repository.DataTypeRepository;
import org.lolobored.plex.elasticsearch.repository.MediaRepository;
import org.lolobored.plex.elasticsearch.search.DataType;
import org.lolobored.plex.model.Media;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

import static org.elasticsearch.index.query.QueryBuilders.regexpQuery;
import static org.elasticsearch.index.query.QueryBuilders.termsQuery;

@Service
public class ElasticSearchServiceImpl implements ElasticSearchService {


	@Autowired
	private DataTypeRepository dataTypeRepository;

	@Autowired
	private MediaRepository mediaRepository;

	@Autowired
	ElasticsearchTemplate elasticsearchTemplate;

	@Autowired
	ElasticSearchConfig elasticSearchConfig;

	private static Logger logger = LoggerFactory.getLogger(ElasticSearchServiceImpl.class);


	/**
	 * Insert any Media object into Elastic Search
	 *
	 * @param media the Media object which will be inserted into Elastic Search
	 * @throws HttpException           in case of an error while calling the REST API from elastic search
	 * @throws JsonProcessingException in case of an error while serializing / deserializing objects using Jackson
	 */
	@Override
	public void insertMedia(Media media) throws IOException, HttpException {
		// Search for media first
		Media existingMedia = mediaRepository.findByUserAndPlexId(media.getUser(), media.getPlexId());

		if (existingMedia != null) {
			media.setId(existingMedia.getId());
		} else {
			media.setId(UUID.randomUUID().toString());
		}

		mediaRepository.save(media);

		// save genre as datatypes for movies
		if (media.getType().equals(Media.MOVIE_TYPE)) {
			for (String genre : media.getGenres()) {
				Optional<DataType> existingGenre = dataTypeRepository.findByTypeAndValue(DataType.GENRE, genre);
				if (!existingGenre.isPresent()) {
					DataType dataType = new DataType();
					dataType.setId(UUID.randomUUID().toString());
					dataType.setType(DataType.GENRE);
					dataType.setValue(genre);
					dataTypeRepository.save(dataType);
				}
			}

		}
		// save year as datatypes for movies
		if (media.getType().equals(Media.MOVIE_TYPE) && media.getYear() != null) {
			Optional<DataType> existingYear = dataTypeRepository.findByTypeAndValue(DataType.YEAR, media.getYear().toString());
			if (!existingYear.isPresent()) {
				DataType dataType = new DataType();
				dataType.setId(UUID.randomUUID().toString());
				dataType.setType(DataType.YEAR);
				dataType.setValue(media.getYear().toString());
				dataTypeRepository.save(dataType);
			}
		}
		// save genre as datatypes for movies
		if (media.getType().equals(Media.EPISODE_TYPE)) {
			Optional<DataType> existingShow = dataTypeRepository.findByTypeAndValue(DataType.TV_SHOW, media.getShow().getShowTitle());
			if (!existingShow.isPresent()) {
				DataType dataType = new DataType();
				dataType.setId(UUID.randomUUID().toString());
				dataType.setType(DataType.TV_SHOW);
				dataType.setValue(media.getShow().getShowTitle());
				dataTypeRepository.save(dataType);
			}

		}
	}

	@Override
	public List<String> getTvShowsList() {
		List<String> result = new ArrayList();
		Pageable pageRequest = new PageRequest(0, 1000, Sort.Direction.ASC, "value.keyword");
		Page<DataType> page = dataTypeRepository.findByType(DataType.TV_SHOW, pageRequest);
		for (DataType dataType : page.getContent()) {
			result.add(dataType.getValue());
		}
		while(result.size() < page.getTotalElements()) {
			pageRequest = pageRequest.next();
			page = dataTypeRepository.findByType(DataType.TV_SHOW, pageRequest);
			for (DataType dataType : page.getContent()) {
				result.add(dataType.getValue());
			}
		}
		return result;
	}

	@Override
	public List<String> getMoviesGenre() {
		List<String> result = new ArrayList();
		Pageable pageRequest = new PageRequest(0, 1000, Sort.Direction.ASC, "value.keyword");
		Page<DataType> page = dataTypeRepository.findByType(DataType.GENRE, pageRequest);
		for (DataType dataType : page.getContent()) {
			result.add(dataType.getValue());
		}
		while(result.size() < page.getTotalElements()) {
			pageRequest = pageRequest.next();
			page = dataTypeRepository.findByType(DataType.GENRE, pageRequest);
			for (DataType dataType : page.getContent()) {
				result.add(dataType.getValue());
			}
		}
		return result;
	}

	@Override
	public List<String> getAllYears() {

		List<String> result = new ArrayList();
		Pageable pageRequest = new PageRequest(0, 1000, Sort.Direction.ASC, "value.keyword");
		Page<DataType> page = dataTypeRepository.findByType(DataType.YEAR, pageRequest);
		for (DataType dataType : page.getContent()) {
			result.add(dataType.getValue());
		}
		while(result.size() < page.getTotalElements()) {
			pageRequest = pageRequest.next();
			page = dataTypeRepository.findByType(DataType.YEAR, pageRequest);
			for (DataType dataType : page.getContent()) {
				result.add(dataType.getValue());
			}
		}
		return result;
	}


	@Override
	public List<Media> getAllMovies(String user) {
		List<Media> result = new ArrayList();
		Pageable pageRequest = new PageRequest(0, 1000, Sort.Direction.ASC, "title.keyword");
		Page<Media> page = mediaRepository.findByUserAndType(user, Media.MOVIE_TYPE, pageRequest);
		result.addAll(page.getContent());
		while(result.size() < page.getTotalElements()) {
			pageRequest = pageRequest.next();
			page = mediaRepository.findByUserAndType(user, Media.MOVIE_TYPE, pageRequest);
			result.addAll(page.getContent());
		}
		return result;

	}

	@Override
	public List<Media> searchMovies(String user, List<String> genre, Integer startYear, Integer endYear) {
		List<Media> result = new ArrayList<>();
		// if genre is empty
		if (genre.isEmpty()) {
			return result;
		}
		Pageable pageRequest = new PageRequest(0, 1000, Sort.Direction.ASC, "title.keyword");
		Page<Media> page = mediaRepository.findByUserAndTypeAndGenresInAndYearGreaterThanEqualAndYearLessThanEqual(user, Media.MOVIE_TYPE, genre, startYear, endYear, pageRequest);
		result.addAll(page.getContent());
		while(result.size() < page.getTotalElements()) {
			pageRequest = pageRequest.next();
			page = mediaRepository.findByUserAndTypeAndGenresInAndYearGreaterThanEqualAndYearLessThanEqual(user, Media.MOVIE_TYPE, genre, startYear, endYear, pageRequest);
			result.addAll(page.getContent());
		}
		return result;
	}

	@Override
	public List<Media> getAllTvShows(String user) {

		List<Media> result = new ArrayList();
		Pageable pageRequest = new PageRequest(0, 1000, Sort.Direction.ASC, "show.showTitle.keyword",
			"season.seasonNumber", "episodeNumber");
		Page<Media> page = mediaRepository.findByUserAndType(user, Media.EPISODE_TYPE, pageRequest);
		result.addAll(page.getContent());
		while(result.size() < page.getTotalElements()) {
			pageRequest = pageRequest.next();
			page = mediaRepository.findByUserAndType(user, Media.EPISODE_TYPE, pageRequest);
			result.addAll(page.getContent());
		}
		return result;

	}

	@Override
	public List<Media> searchTvShows(String username, List<String> showTitles) throws HttpException {
		setMaxResultWindow(elasticSearchConfig.getESHttpUrl(), elasticSearchConfig.getESMaxResult());
		List<Media> result = new ArrayList<>();
		Pageable pageRequest = new PageRequest(0, 1000, Sort.Direction.ASC, "show.showTitle.keyword",
			"season.seasonNumber", "episodeNumber");
		SearchQuery searchQuery = new NativeSearchQueryBuilder()
				.withFilter(  termsQuery("show.showTitle.keyword", showTitles))
			.withPageable(pageRequest)
				.build();
		Page<Media> page = elasticsearchTemplate
				.queryForPage(searchQuery, Media.class);
		result.addAll(page.getContent());
		while(result.size() < page.getTotalElements()) {
			pageRequest = pageRequest.next();
			searchQuery = new NativeSearchQueryBuilder()
				.withFilter(  termsQuery("show.showTitle.keyword", showTitles))
				.withPageable(pageRequest)
				.build();
			page = elasticsearchTemplate
				.queryForPage(searchQuery, Media.class);
			result.addAll(page.getContent());
		}
		return result;
	}


	private void setMaxResultWindow(String url, Integer maxResult) throws HttpException {
		HttpUtil httpUtil = HttpUtil.getInstance(false);
		String json= "{\n" +
			"  \"index.max_result_window\" : \""+maxResult+"\"\n" +
			"}";
		Map<String, String> header= new HashMap<>();
		header.put("Content-Type", "application/json;charset=UTF-8");
		httpUtil.put(url+"/media/_settings", json, header);
	}

}
