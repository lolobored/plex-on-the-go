package org.lolobored.plex.spring.tasks;

import org.lolobored.http.HttpException;
import org.lolobored.plex.PlexService;
import org.lolobored.plex.spring.repository.ConversionRepository;
import org.lolobored.plex.elasticsearch.ElasticSearchService;
import org.lolobored.plex.model.Media;
import org.lolobored.plex.spring.config.PlexConfig;
import org.lolobored.plex.spring.models.User;
import org.lolobored.plex.spring.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class ESLoaderTask {

	private static final Logger log = LoggerFactory.getLogger(ESLoaderTask.class);


	@Autowired
	UserService userService;

	@Autowired
	ElasticSearchService elasticSearchService;

	@Autowired
	PlexService plexService;

	@Autowired
	PlexConfig plexConfig;

	@Autowired
	ConversionRepository conversionRepository;

	@Scheduled(fixedRate = 1800000)
	public void loadElasticSearch() {

		List<User> users = userService.getUsers();
		for (User user : users) {
			log.debug(String.format("User [%s] was authenticated by Plex", user.getUserName()));
			if (user.isPlexTokenValidated()) {
				List<Media> movies = null;
				try {
					movies = plexService.exploreMovies(plexConfig.getUrl(),
						user.getPlexToken(),
						user.getPlexLogin(),
						true);
					log.debug(String.format("User [%s] has a list of [%d] movies", user.getUserName(), movies.size()));
					for (Media movie : movies) {
						elasticSearchService.insertMedia(movie);
						log.debug(String.format("Movie [%s][%s] was added", movie.getTitle(), movie.getFileLocation()));
					}
				} catch (HttpException e) {
					log.error("HTTPException encountered while trying to explore plex libraries", e);
				} catch (IOException e) {
					log.error("IOException encountered while trying to explore plex libraries", e);
				}
			}
		}
	}

}
