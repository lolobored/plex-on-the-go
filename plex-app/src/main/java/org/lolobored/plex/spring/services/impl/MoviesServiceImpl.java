package org.lolobored.plex.spring.services.impl;

import org.lolobored.plex.elasticsearch.ElasticSearchService;
import org.lolobored.plex.model.Media;
import org.lolobored.plex.spring.converter.ConversionJob;
import org.lolobored.plex.spring.models.Converted;
import org.lolobored.plex.elasticsearch.search.SearchRequest;
import org.lolobored.plex.elasticsearch.search.SearchResponse;
import org.lolobored.plex.spring.services.ConversionService;
import org.lolobored.plex.spring.services.MoviesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class MoviesServiceImpl implements MoviesService {


	@Autowired
	private ElasticSearchService elasticSearch;

	@Autowired
	private ConversionService conversionService;

	@Override
	public List<String> getGenres() {
		return elasticSearch.getMoviesGenre();
	}

	@Override
	public List<String> getYears() {
		return elasticSearch.getAllYears();
	}

	@Override
	public SearchResponse searchMovies(String username, SearchRequest searchRequest) {

		SearchResponse response = elasticSearch.searchMovies(username, searchRequest.getGenres(), searchRequest.getYearFrom(), searchRequest.getYearTo(),
			searchRequest.getPageNumber(), searchRequest.getResultPerPage());
		generateConversionProperty(response);
		return response;
	}

	private void generateConversionProperty(SearchResponse response) {
		Set<String> convertedIds = new HashSet<>();
		// get converted and Conversion
		List<ConversionJob> pendings = conversionService.getPendingConversionJobs();
		for (ConversionJob pending : pendings) {
			convertedIds.add(pending.getPendingConversion().getId());
		}
		List<Converted> dones = conversionService.getConverted();
		for (Converted done : dones) {
			convertedIds.add(done.getId());
		}
		for (Media movie : response.getResults()) {
			if (convertedIds.contains(movie.getId())) {
				movie.setConverted(true);
			}

		}
	}
}
