package org.lolobored.plex.spring.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.lolobored.plex.model.Media;
import org.lolobored.plex.spring.converter.ConversionJob;
import org.lolobored.plex.spring.models.Converted;
import org.lolobored.plex.spring.models.PendingConversion;
import org.lolobored.plex.spring.models.RunningConversion;
import org.lolobored.plex.spring.models.User;

import java.io.IOException;
import java.util.List;

public interface ConversionService {

	List<RunningConversion> getPendingConversions() throws IOException;

	void addConversion(Media media, String userid) throws JsonProcessingException;

	List<ConversionJob> getPendingConversionJobs();

	List<Converted> getConverted();

	void moveToConverted(PendingConversion pendingConversion) throws IOException;

	ConversionJob getNextJob();
}
