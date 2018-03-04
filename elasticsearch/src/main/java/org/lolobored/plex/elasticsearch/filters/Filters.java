package org.lolobored.plex.elasticsearch.filters;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.lolobored.plex.elasticsearch.query.Must;
import org.lolobored.plex.elasticsearch.query.Should;
import org.lolobored.plex.model.Media;

import java.util.ArrayList;
import java.util.List;

/**
 * Filters is supposed to be used in Bool search in elastic search
 * It uses a List of single Filter to do so
 * We can return the objects as Must (list of and condition) or Should (list of Or condition)
 * @see <a href="https://www.elastic.co/guide/en/elasticsearch/reference/6.2/query-dsl-bool-query.html">Bool Query</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Filters {

	private List<Filter> filters = new ArrayList<>();

	/**
	 * Add a filter on the user of the plex library
	 * @param username
	 */
	public void addUser(String username){
		Filter filter = new Filter();
		filter.setUser(username);
		this.addFilter(filter);
	}

	/**
	 * Add a filter for filtering only on type Movie
	 */
	public void addTypeMovie(){
		Filter filter = new Filter();
		filter.setType(Media.MOVIE_TYPE);
		this.addFilter(filter);
	}

	/**
	 * Add a filter for filtering only on tvshows
	 */
	public void addTypeTvShow(){
		Filter filter = new Filter();
		filter.setType(Media.EPISODE_TYPE);
		this.addFilter(filter);
	}

	/**
	 * Add a filter on genre (ie Action, Adventure)
	 * @param genre
	 */
	public void addGenre(String genre){
		Filter filter = new Filter();
		filter.setGenre(genre);
		this.addFilter(filter);
	}

	/**
	 * Add a filter on the season number
	 * @param seasonNumber
	 */
	public void addSeason(Integer seasonNumber){
		Filter filter = new Filter();
		filter.setSeason(seasonNumber);
		this.addFilter(filter);
	}

	/**
	 * Add a filter on the show title
	 * @param title
	 */
	public void addShow(String title){
		Filter filter = new Filter();
		filter.setShowTitle(title);
		this.addFilter(filter);
	}

	/**
	 * helper to add any filter to the list of filter
	 * @param filter
	 */
	private void addFilter(Filter filter){
		filters.add(filter);
	}

	/**
	 * Retrieve filter as a list of Must (and condition)
	 * @return
	 */
	public List<Must> getFiltersAsMust(){
		List<Must> musts = new ArrayList<>();
		for (Filter filter: filters){
			Must must= new Must();
			must.setFilter(filter);
			musts.add(must);
		}
		return musts;
	}

	/**
	 * retrieve filters as a list of should (or condition)
	 * @return
	 */
	public List<Should> getFiltersAsShould(){
		List<Should> shoulds = new ArrayList<>();
		for (Filter filter: filters){
			Should should= new Should();
			should.setFilter(filter);
			shoulds.add(should);
		}
		return shoulds;
	}
}
