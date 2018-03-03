package org.lolobored.plex.apis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.lolobored.http.HttpException;
import org.lolobored.http.HttpUtil;
import org.lolobored.plex.apis.token.AccessToken;
import org.lolobored.plex.model.*;
import org.lolobored.plex.objects.mediacontainer.Directory;
import org.lolobored.plex.objects.mediacontainer.MediaType;
import org.lolobored.plex.objects.mediacontainer.Section;
import org.lolobored.plex.objects.metadata.Genre;
import org.lolobored.plex.objects.metadata.MediaPlex;
import org.lolobored.plex.objects.metadata.Metadata;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlexApis {

	private static final String plexUrl = "https://plex.tv/users/sign_in.json";
	private static ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

	public static String authenticate(String userName, String password) throws HttpException, IOException {

		Map<String, String> httpHeaders = new HashMap();
		httpHeaders.put("Content-Type", HttpUtil.FORM_URLENCODED);
		httpHeaders.put("X-Plex-Client-Identifier", "plex-on-the-go");
		httpHeaders.put("charset", "utf-8");

		List<NameValuePair> nameValuePairs = new ArrayList<>();

		nameValuePairs.add(new BasicNameValuePair("user[login]", userName));
		nameValuePairs.add(new BasicNameValuePair("user[password]", password));
		String response = HttpUtil.getInstance(false).postUrlEncoded(plexUrl, nameValuePairs, httpHeaders);
		AccessToken token = mapper.readValue(response, AccessToken.class);
		return token.getUser().getAuthentication_token();
	}

	public static List<Media> exploreMovies(String baseUrl, String token, String user, boolean bypassSSL) throws HttpException, IOException {
		List<Media> movies = new ArrayList();

		String mediaLibrariesJson = HttpUtil.getInstance(bypassSSL).get(String.format("%s/library/sections", baseUrl), getPlexHttpHeaders(token));
		Section mediaLibraries = mapper.readValue(mediaLibrariesJson, Section.class);
		for (Directory directory : mediaLibraries.getMediaContainer().getDirectory()) {

			if (MediaType.MOVIE_TYPE.equals(directory.getType())) {
				String library = directory.getTitle();
				String moviesJson = HttpUtil.getInstance(bypassSSL).get(String.format("%s/library/sections/%s/all", baseUrl, directory.getKey().toString()), getPlexHttpHeaders(token));
				Section moviesSection = mapper.readValue(moviesJson, Section.class);

				for (Metadata metadata : moviesSection.getMediaContainer().getMetadata()) {

					for (MediaPlex plexMedia : metadata.getMedia()) {
						Media movie = new Media();
						loadMediaInfo(movie, Media.MOVIE_TYPE, metadata, plexMedia, null, null, library, user);
						movies.add(movie);
					}
				}
			}
		}
		return movies;
	}

	public static List<Media> exploreTvShows(String baseUrl, String token, String user, boolean bypassSSL) throws HttpException, IOException {
		List<Media> episodesList = new ArrayList();
		Season season;
		Show show;
		String mediaLibrariesJson = HttpUtil.getInstance(bypassSSL).get(String.format("%s/library/sections", baseUrl), getPlexHttpHeaders(token));
		Section mediaLibraries = mapper.readValue(mediaLibrariesJson, Section.class);
		for (Directory directory : mediaLibraries.getMediaContainer().getDirectory()) {
			if (MediaType.TV_SHOW_TYPE.equals(directory.getType())) {
				String library = directory.getTitle();
				String tvShowsJson = HttpUtil.getInstance(bypassSSL).get(String.format("%s/library/sections/%s/all", baseUrl, directory.getKey().toString()), getPlexHttpHeaders(token));
				Section tvshows = mapper.readValue(tvShowsJson, Section.class);
				for (Metadata showMetadata : tvshows.getMediaContainer().getMetadata()) {
					show = parseShow(showMetadata);

					String seasonsJson = HttpUtil.getInstance(bypassSSL).get(String.format("%s/%s", baseUrl, showMetadata.getKey().toString()), getPlexHttpHeaders(token));
					Section seasons = mapper.readValue(seasonsJson, Section.class);
					for (Metadata seasonMedata : seasons.getMediaContainer().getMetadata()) {
						season = new Season();
						season.setSeasonNumber(seasonMedata.getIndex());
						String episodesJson = HttpUtil.getInstance(bypassSSL).get(String.format("%s/%s", baseUrl, seasonMedata.getKey().toString()), getPlexHttpHeaders(token));
						Section episodes = mapper.readValue(episodesJson, Section.class);
						for (Metadata episodeMetadata : episodes.getMediaContainer().getMetadata()) {

							for (MediaPlex plexMedia : episodeMetadata.getMedia()) {
								Media episode = new Media();
								loadMediaInfo(episode, Media.EPISODE_TYPE, episodeMetadata, plexMedia, show, season, library, user);
								episodesList.add(episode);
							}
						}
					}
				}
			}
		}
		return episodesList;
	}

	private static Show parseShow(Metadata metadata) {
		Show show = new Show();
		show.setShowTitle(metadata.getTitle());
		show.setContentRating(metadata.getContentRating());
		show.setRating(metadata.getRating());
		if (metadata.getOriginallyAvailableAt() != null) {
			show.setStartDate(LocalDate.parse(metadata.getOriginallyAvailableAt()));
		}
		show.setStudio(metadata.getStudio());
		show.setSummary(metadata.getSummary());
		show.setYear(metadata.getYear());
		return show;
	}

	private static void loadMediaInfo(Media media, String type, Metadata metadata, MediaPlex plexMedia, Show show, Season season, String library, String user) {
		media.setType(type);
		media.setFileLocation(plexMedia.getPart().get(0).getFile());
		media.setRating(metadata.getRating());
		media.setSummary(metadata.getSummary());
		media.setTitle(metadata.getTitle());
		media.setYear(metadata.getYear());
		media.setUser(user);
		media.setLibrary(library);

		List<Genre> genresMeta= metadata.getGenre();
		if (genresMeta!=null){
			List<String> genres = new ArrayList<>();
			for (Genre singleGenre: genresMeta){
				genres.add(singleGenre.getTag());
			}
			media.setGenres(genres);
		}

		media.setSeason(season);
		media.setShow(show);
		media.setEpisode(metadata.getIndex());
		if (metadata.getOriginallyAvailableAt() != null) {
			media.setStartDate(LocalDate.parse(metadata.getOriginallyAvailableAt()));
		}

	}

	private static Map<String, String> getPlexHttpHeaders(String token) {
		Map<String, String> httpHeaders = new HashMap();
		httpHeaders.put("Content-Type", "application/json;charset=UTF-8");
		httpHeaders.put("Accept", "application/json");
		httpHeaders.put("X-Plex-Token", token);
		return httpHeaders;
	}
}


