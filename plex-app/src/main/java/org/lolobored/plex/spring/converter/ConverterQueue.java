package org.lolobored.plex.spring.converter;

import java.util.ArrayList;
import java.util.List;

public class ConverterQueue {

	private List<ConversionJob> conversionQueue= new ArrayList<>();



	public void addJob(ConversionJob job){
		conversionQueue.add(job);
	}

	public ConversionJob getNextJob(){
		if (!conversionQueue.isEmpty()) {
			return conversionQueue.get(0);
		}
		else{
			return null;
		}
	}

	public void removeJob(){
		conversionQueue.remove(0);
	}

	public void updateJobWithProgress(ConversionJob job, ConversionProgress progress){

	}

	public void removeAllJobs(){
		conversionQueue.clear();
	}

	public boolean isEmpty() {
		return conversionQueue.isEmpty();
	}

	public List<ConversionJob> getAllJobs() {
		return conversionQueue;
	}
}
