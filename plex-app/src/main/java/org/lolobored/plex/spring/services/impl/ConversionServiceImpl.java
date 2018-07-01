package org.lolobored.plex.spring.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import net.bramp.ffmpeg.FFmpegUtils;
import org.apache.commons.io.FileUtils;
import org.lolobored.plex.model.Media;
import org.lolobored.plex.spring.config.ConverterConfig;
import org.lolobored.plex.spring.converter.ConversionJob;
import org.lolobored.plex.spring.converter.ConversionProgress;
import org.lolobored.plex.spring.converter.ConverterQueue;
import org.lolobored.plex.spring.models.Converted;
import org.lolobored.plex.spring.models.PendingConversion;
import org.lolobored.plex.spring.models.RunningConversion;
import org.lolobored.plex.spring.models.User;
import org.lolobored.plex.spring.repository.ConversionRepository;
import org.lolobored.plex.spring.repository.ConvertedRepository;
import org.lolobored.plex.spring.services.ConversionService;
import org.lolobored.plex.spring.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ConversionServiceImpl implements ConversionService {

	@Autowired
	ConversionRepository conversionRepository;
	@Autowired
	ConvertedRepository convertedRepository;

	@Autowired
	ConverterConfig converterConfig;


	@Autowired
	UserService userService;

	@Resource(name = "conversionQueue")
	ConverterQueue converterQueue;

	@Override
	public List<RunningConversion> getPendingConversions() throws IOException {
		List<ConversionJob> converterJobs = converterQueue.getAllJobs();
		List<RunningConversion> result = new ArrayList<>();
		for (ConversionJob converterJob : converterJobs) {
			RunningConversion runningConversion = new RunningConversion();
			if (converterJob.getConversionProgress().getElapsedTime() > 0) {
				runningConversion.setElapsedTime(FFmpegUtils.millisecondsToString(converterJob.getConversionProgress().getElapsedTime()));
				runningConversion.setEstimatedRemaining(FFmpegUtils.millisecondsToString(converterJob.getConversionProgress().getRemainingTime()));
			} else {
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
	public ConversionJob getNextJob(){
		return converterQueue.getNextJob();
	}

	@Override
	public List<ConversionJob> getPendingConversionJobs(){
		return converterQueue.getAllJobs();
	}

	@Override
	public List<Converted> getConverted() {
		return convertedRepository.findAll();
	}

	@Override
	public void addConversion(Media media, String userid) throws JsonProcessingException {

		Optional<PendingConversion> conversionOpt = conversionRepository.findById(media.getId());
		if (!conversionOpt.isPresent()) {
			PendingConversion pendingConversion = new PendingConversion();
			pendingConversion.setId(media.getId());
			pendingConversion.setCreationDateTime(LocalDateTime.now());
			User user = userService.getUserById(userid);
			pendingConversion.setUser(user);
			pendingConversion.setDone(false);
			pendingConversion.setMedia(media);
			conversionRepository.save(pendingConversion);
			ConversionJob conversionJob = new ConversionJob();
			conversionJob.setPendingConversion(pendingConversion);
			conversionJob.setConversionProgress(new ConversionProgress());
			converterQueue.addJob(conversionJob);
		}

	}

	@Override
	public void moveToConverted(PendingConversion pendingConversion, String convertedFilePath) throws IOException {
		Media media= pendingConversion.getMediaAsObject();

		File sourceFile= new File(convertedFilePath);
		String path= pendingConversion.getUser().getHomeDirectory();
		path+="/movies/";
		if (media.getGenres().isEmpty()){
			path+="unknown";
		}
		else{
			path+=media.getGenres().get(0).toLowerCase();
		}
		path+="/";
		path+=media.getTitle().toLowerCase()+" ["+media.getYear()+"]";
		File directory= new File(path);
		directory.mkdirs();
		path+="/"+media.getTitle().toLowerCase()+" ["+media.getYear()+"]-720p.m4v";
		File targetFile=new File(path);
		sourceFile.renameTo(targetFile);

		// create the done entry
		Converted converted= new Converted();
		converted.setId(pendingConversion.getId());
		converted.setConvertedDateTime(LocalDateTime.now());
		converted.setFinalPath(targetFile.getAbsolutePath());
		converted.setMedia(pendingConversion.getMediaAsObject());
		converted.setUser(pendingConversion.getUser());
		// change ownership
		Runtime.getRuntime().exec("chown -R "+pendingConversion.getUser().getOwnership()+" "+pendingConversion.getUser().getHomeDirectory());

		conversionRepository.delete(pendingConversion);
		converterQueue.removeJob();
		convertedRepository.save(converted);

	}
}
