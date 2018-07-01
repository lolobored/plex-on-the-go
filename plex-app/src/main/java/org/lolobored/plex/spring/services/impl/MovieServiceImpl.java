package org.lolobored.plex.spring.services.impl;

import org.lolobored.plex.elasticsearch.ElasticSearchService;
import org.lolobored.plex.model.Media;
import org.lolobored.plex.spring.converter.ConversionJob;
import org.lolobored.plex.spring.models.Converted;
import org.lolobored.plex.spring.models.Search;
import org.lolobored.plex.spring.services.ConversionService;
import org.lolobored.plex.spring.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class MovieServiceImpl implements MovieService {


	@Autowired
	private ElasticSearchService elasticSearch;

	@Autowired
	private ConversionService conversionService;

	@Override
	public List<String> getGenres() {
		return elasticSearch.getMoviesGenre();
	}

	@Override
	public List<Integer> getYears() {
		return elasticSearch.getAllYears();
	}

	@Override
	public List<Media> getMovies(String user) {

		List<Media> movies = elasticSearch.getAllMovies(user);
		generateConversionProperty(movies);
		return movies;
	}

	@Override
	public List<Media> searchMovies(String username, Search search) {

		List<Media> movies = elasticSearch.searchMovies(username, search.getGenres(), search.getYearFrom(), search.getYearTo());
		generateConversionProperty(movies);
		return movies;
	}

	private void generateConversionProperty(List<Media> media) {
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
		for (Media movie : media) {
			if (convertedIds.contains(movie.getId())) {
				movie.setConverted(true);
			}

		}
	}
}
