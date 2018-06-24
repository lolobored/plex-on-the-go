package org.lolobored.plex.spring.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import net.bramp.ffmpeg.FFmpegUtils;
import org.apache.tomcat.jni.Local;
import org.lolobored.plex.model.Media;
import org.lolobored.plex.spring.converter.ConversionJob;
import org.lolobored.plex.spring.converter.ConverterQueue;
import org.lolobored.plex.spring.models.Conversion;
import org.lolobored.plex.spring.models.RunningConversion;
import org.lolobored.plex.spring.repository.ConversionRepository;
import org.lolobored.plex.spring.services.ConversionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import java.util.List;

@Service
public class ConversionServiceImpl implements ConversionService {

	@Autowired
	ConversionRepository conversionRepository;

	@Resource(name = "conversionQueue")
	ConverterQueue converterQueue;

	@Override
	public List<RunningConversion> getPendingConversions() throws IOException {
		List<ConversionJob> converterJobs = converterQueue.getAllJobs();
		List<RunningConversion> result= new ArrayList<>();
		for (ConversionJob converterJob: converterJobs){
			RunningConversion runningConversion= new RunningConversion();
			if (converterJob.getConversionProgress().getElapsedTime()> 0){
				runningConversion.setElapsedTime(FFmpegUtils.millisecondsToString(converterJob.getConversionProgress().getElapsedTime()));
				runningConversion.setEstimatedRemaining(FFmpegUtils.millisecondsToString(converterJob.getConversionProgress().getRemainingTime()));
			}
			else{
				runningConversion.setElapsedTime("Not started");
				runningConversion.setEstimatedRemaining("Undefined");
			}
			runningConversion.setPercentage(converterJob.getConversionProgress().getPercentage());
			runningConversion.setTitle(converterJob.getConversion().getMediaAsObject().getTitle());
			result.add(runningConversion);
		}
		return result;
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
