package org.lolobored.plex.spring.services.impl;

import org.lolobored.http.HttpException;
import org.lolobored.plex.elasticsearch.ElasticSearchService;
import org.lolobored.plex.model.Media;
import org.lolobored.plex.spring.converter.ConversionJob;
import org.lolobored.plex.spring.models.Converted;
import org.lolobored.plex.spring.models.Search;
import org.lolobored.plex.spring.services.ConversionService;
import org.lolobored.plex.spring.services.TvShowsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class TvShowsServiceImpl implements TvShowsService {


	@Autowired
	private ElasticSearchService elasticSearch;

	@Autowired
	private ConversionService conversionService;

	@Override
	public List<String> getTvShowsList() {
		return elasticSearch.getTvShowsList();
	}

	@Override
	public List<Media> getTvShows(String user) {

		List<Media> movies = elasticSearch.getAllTvShows(user);
		generateConversionProperty(movies);
		return movies;
	}

	@Override
	public List<Media> searchTvShows(String username, Search search) throws HttpException {

		List<Media> movies = elasticSearch.searchTvShows(username, search.getShowTitles());
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
		for (Media tvshow : media) {
			if (convertedIds.contains(tvshow.getId())) {
				tvshow.setConverted(true);
			}

		}
	}
}
