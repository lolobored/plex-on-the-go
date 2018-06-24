package org.lolobored.plex.spring.tasks;

import net.bramp.ffmpeg.progress.Progress;
import net.bramp.ffmpeg.progress.ProgressListener;
import org.lolobored.plex.spring.converter.ConversionProgress;

public class ConverterProgressListener implements ProgressListener {

	private final ConversionProgress conversionProgress;
	private final long totalDurationMs;
	private final long startTime = System.currentTimeMillis();

	public ConverterProgressListener(ConversionProgress conversionProgress, long totalDurationMs){
		this.conversionProgress=conversionProgress;
		this.totalDurationMs= totalDurationMs;
	}
	
	@Override
	public void progress(Progress progress) {
		long currentTime = System.currentTimeMillis();
		long elapsedTime= currentTime-startTime;
		long current_ms = progress.out_time_ms/1000;
		double percentage = (double)current_ms / totalDurationMs * 100;
		long remaining_ms = (long)(elapsedTime/percentage) - elapsedTime;
		if (remaining_ms<0){
			remaining_ms=0;
		}

		conversionProgress.setElapsedTime(elapsedTime);
		conversionProgress.setPercentage(percentage);
		conversionProgress.setRemainingTime(remaining_ms);
		conversionProgress.setSpeed(progress.fps.floatValue());
	}
}
