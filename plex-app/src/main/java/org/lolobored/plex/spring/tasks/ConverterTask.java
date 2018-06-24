package org.lolobored.plex.spring.tasks;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.builder.FFmpegOutputBuilder;
import net.bramp.ffmpeg.job.FFmpegJob;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import org.lolobored.plex.spring.config.ConverterConfig;
import org.lolobored.plex.spring.converter.ConversionJob;
import org.lolobored.plex.spring.converter.ConverterQueue;
import org.lolobored.plex.spring.models.Conversion;
import org.lolobored.plex.spring.converter.ConversionProgress;
import org.lolobored.plex.spring.repository.ConversionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
public class ConverterTask {

	private static final Logger log = LoggerFactory.getLogger(ConverterTask.class);

	@Autowired
	ConversionRepository conversionRepository;

	@Autowired
	ConverterConfig converterConfig;

	@Resource(name = "conversionQueue")
	ConverterQueue converterQueue;

	@Scheduled(fixedRate = 10000)
	public void convertMedia() throws IOException, InterruptedException {
		List<Conversion> listOfMedia = conversionRepository.findAllByDoneFalseOrderByCreationDate();

		converterQueue.removeAllJobs();
		// load the conversion queue
		for (Conversion conversion: listOfMedia){
			ConversionJob conversionJob= new ConversionJob();
			conversionJob.setConversion(conversion);
			conversionJob.setConversionProgress(new ConversionProgress());
			converterQueue.addJob(conversionJob);
		}

		if (!converterQueue.isEmpty()) {
			ConversionJob conversionJob= converterQueue.getNextJob();
			Conversion conversion = conversionJob.getConversion();
			ConversionProgress conversionProgress = conversionJob.getConversionProgress();

			FFmpeg ffmpeg = new FFmpeg(converterConfig.getFfmpeg());
			FFprobe ffprobe = new FFprobe(converterConfig.getFfprobe());

			FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);

			FFmpegProbeResult in = ffprobe.probe(conversion.getMediaAsObject().getFileLocation());

			FFmpegOutputBuilder ffmpegBuilder = new FFmpegBuilder()
				.setInput(in) // Or filename
				.overrideOutputFiles(true)
				.addOutput(converterConfig.getTempDirectory() + "/converting.m4v")
				.setFormat("mp4")
				.setVideoResolution(1280, 720)
				.setAudioChannels(2)         // Mono audio
				.setAudioCodec("aac")        // using the aac codec
				.setAudioSampleRate(48000)  // at 48KHz
				.setAudioBitRate(256000);     // at 256 kbit/s
			ffmpegBuilder.addExtraArgs("-c:s");
			ffmpegBuilder.addExtraArgs("copy");

			for (Map.Entry<String, String> tag : in.getFormat().tags.entrySet()) {
				if (!"".equals(tag.getValue())) {
					ffmpegBuilder.addMetaTag(tag.getKey(), tag.getValue());
				}
			}
			ConverterProgressListener converterProgressListener = new ConverterProgressListener(conversionProgress, (long) in.getFormat().duration);

			FFmpegJob job = executor.createJob(ffmpegBuilder.done(), converterProgressListener);
			job.run();

			while (true) {
				Thread.sleep(10000);
				if (job.getState() != FFmpegJob.State.RUNNING) {
					conversion.setDone(true);
					conversionRepository.save(conversion);
				}
			}
		}
	}
}
