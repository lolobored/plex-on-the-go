package org.lolobored.plex.elasticsearch;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.lolobored.http.HttpException;
import org.lolobored.plex.model.Media;

import java.io.IOException;
import java.util.List;

public interface ElasticSearchService {

	/**
	 * Insert any Media object into Elastic Search
	 *
	 * @param elasticSearchUrl the base url for Elastic search (ie http://localhost:9200)
	 * @param media            the Media object which will be inserted into Elastic Search
	 * @param bypassSSL        determine whether or not we have to bypassSSL checks (for self-generated SSL certificates)
	 * @throws HttpException           in case of an error while calling the REST API from elastic search
	 * @throws JsonProcessingException in case of an error while serializing / deserializing objects using Jackson
	 */
	public void insertMedia(Media media, String elasticSearchUrl, boolean bypassSSL) throws IOException, HttpException;

	public List<String> getMoviesGenre();

	public List<Integer> getAllYears();

	public List<Media> getAllMovies();

	public List<Media> searchMovies(List<String> genre);
}
