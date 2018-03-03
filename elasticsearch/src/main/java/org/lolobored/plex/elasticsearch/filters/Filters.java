package org.lolobored.plex.elasticsearch.filters;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.lolobored.plex.elasticsearch.query.Must;
import org.lolobored.plex.elasticsearch.query.Should;
import org.lolobored.plex.model.Media;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Filters {

	private List<Filter> filters = new ArrayList<>();

	public void addUser(String username){
		Filter filter = new Filter();
		filter.setUser(username);
		this.addFilter(filter);
	}

	public void addTypeMovie(){
		Filter filter = new Filter();
		filter.setType(Media.MOVIE_TYPE);
		this.addFilter(filter);
	}

	public void addTypeTvShow(){
		Filter filter = new Filter();
		filter.setType(Media.EPISODE_TYPE);
		this.addFilter(filter);
	}

	public void addGenre(String genre){
		Filter filter = new Filter();
		filter.setGenre(genre);
		this.addFilter(filter);
	}

	public void addSeason(Integer seasonNumber){
		Filter filter = new Filter();
		filter.setSeason(seasonNumber);
		this.addFilter(filter);
	}

	public void addShow(String title){
		Filter filter = new Filter();
		filter.setShowTitle(title);
		this.addFilter(filter);
	}

	private void addFilter(Filter filter){
		filters.add(filter);
	}

	public List<Must> getFiltersAsMust(){
		List<Must> musts = new ArrayList<>();
		for (Filter filter: filters){
			Must must= new Must();
			must.setFilter(filter);
			musts.add(must);
		}
		return musts;
	}

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
