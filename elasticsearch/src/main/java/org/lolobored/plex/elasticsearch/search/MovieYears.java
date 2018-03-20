package org.lolobored.plex.elasticsearch.search;

import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Data
public class MovieYears {

	public static MovieYears instance = new MovieYears();
	private List<Integer> years = new ArrayList<>();
	private Lock l = new ReentrantLock();

	public static MovieYears getInstance(){
		return instance;
	}

	public void addYear(Integer year){
		l.lock();
		try {
			if (!years.contains(year)){
				years.add(year);
				Collections.sort(years);
			}
		} finally {
			l.unlock();
		}
	}
}
