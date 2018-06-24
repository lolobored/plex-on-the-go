package org.lolobored.plex.spring.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.tomcat.jni.Local;
import org.lolobored.plex.model.Media;
import org.lolobored.plex.spring.models.Conversion;
import org.lolobored.plex.spring.repository.ConversionRepository;
import org.lolobored.plex.spring.services.ConversionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import java.util.List;

@Service
public class ConversionServiceImpl implements ConversionService {

	@Autowired
	ConversionRepository conversionRepository;

	@Override
	public List<Conversion> getConversions() {
		return conversionRepository.findAllByDoneFalseOrderByCreationDate();
	}

	@Override
	public void addConversion(Media media) throws JsonProcessingException {

		Optional<Conversion> conversionOpt = conversionRepository.findById(media.getId());
		if (!conversionOpt.isPresent()) {
			Conversion conversion = new Conversion();
			conversion.setId(media.getId());
			conversion.setCreationDate(LocalDateTime.now());
			conversion.setUserName(media.getUser());
			conversion.setDone(false);
			conversion.setMedia(media);
			conversionRepository.save(conversion);
		}
	}
}
