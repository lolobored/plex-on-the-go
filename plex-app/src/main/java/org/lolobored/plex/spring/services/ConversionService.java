package org.lolobored.plex.spring.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.lolobored.plex.model.Media;
import org.lolobored.plex.spring.models.Conversion;
import org.lolobored.plex.spring.models.Search;

import java.util.List;

public interface ConversionService {

    List<Conversion> getConversions();
    void addConversion(Media media) throws JsonProcessingException;
}
