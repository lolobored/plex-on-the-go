package org.lolobored.plex.elasticsearch.search;

import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Data
public class MovieGenres {

	public static MovieGenres instance = new MovieGenres();
	private List<String> genres = new ArrayList<>();
	private Lock l = new ReentrantLock();

	public static MovieGenres getInstance(){
		return instance;
	}

	public void addGenre(String genre){
		l.lock();
		try {
			if (!genres.contains(genre)){
				genres.add(genre);
				Collections.sort(genres);
			}
		} finally {
			l.unlock();
		}
	}

	public void addGenres(List<String> addedGenres) {
		l.lock();
		try {
			if (addedGenres != null) {
				for (String genre : addedGenres) {
					addGenre(genre);
				}
			}
		} finally {
			l.unlock();
		}
	}
}
