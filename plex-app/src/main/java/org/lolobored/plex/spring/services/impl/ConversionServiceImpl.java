package org.lolobored.plex.spring.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import net.bramp.ffmpeg.FFmpegUtils;
import org.lolobored.plex.model.Media;
import org.lolobored.plex.spring.converter.ConversionJob;
import org.lolobored.plex.spring.converter.ConverterQueue;
import org.lolobored.plex.spring.models.PendingConversion;
import org.lolobored.plex.spring.models.RunningConversion;
import org.lolobored.plex.spring.models.User;
import org.lolobored.plex.spring.repository.ConversionRepository;
import org.lolobored.plex.spring.services.ConversionService;
import org.lolobored.plex.spring.services.UserService;
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

	@Autowired
	UserService userService;

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
			runningConversion.setTitle(converterJob.getPendingConversion().getMediaAsObject().getTitle());
			result.add(runningConversion);
		}
		return result;
	}

	@Override
	public void addConversion(Media media, String userid) throws JsonProcessingException {

		Optional<PendingConversion> conversionOpt = conversionRepository.findById(media.getId());
		if (!conversionOpt.isPresent()) {
			PendingConversion pendingConversion = new PendingConversion();
			pendingConversion.setId(media.getId());
			pendingConversion.setCreationDateTime(LocalDateTime.now());
			User user= userService.getUserById(userid);
			pendingConversion.setUser(user);
			pendingConversion.setDone(false);
			pendingConversion.setMedia(media);
			conversionRepository.save(pendingConversion);
		}
	}
}
