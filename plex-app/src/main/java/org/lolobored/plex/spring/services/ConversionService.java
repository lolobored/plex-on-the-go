package org.lolobored.plex.spring.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.lolobored.plex.model.Media;
import org.lolobored.plex.spring.models.Conversion;
import org.lolobored.plex.spring.models.RunningConversion;
import org.lolobored.plex.spring.models.Search;

import java.io.IOException;
import java.util.List;

public interface ConversionService {

    List<RunningConversion> getPendingConversions() throws IOException;
    void addConversion(Media media, String userid) throws JsonProcessingException;
}
