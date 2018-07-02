package org.lolobored.plex.spring.services;

import org.lolobored.plex.model.Media;
import org.lolobored.plex.spring.models.Search;

import java.util.List;

public interface TvShowsService {

	List<Media> getTvShows(String user);

	List<Media> searchTvShows(String username, Search search);
}
