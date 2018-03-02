package org.lolobored.plex.apis;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.lolobored.http.HttpException;
import org.lolobored.http.HttpUtil;
import org.lolobored.plex.model.*;
import org.lolobored.plex.apis.token.AccessToken;
import org.lolobored.plex.objects.mediacontainer.Directory;
import org.lolobored.plex.objects.mediacontainer.MediaType;
import org.lolobored.plex.objects.mediacontainer.Section;
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
    private static ObjectMapper mapper = new ObjectMapper();

    public static String authenticate(String userName, String password) throws HttpException, IOException {

        Map<String, String> httpHeaders = new HashMap();
        httpHeaders.put("Content-Type", HttpUtil.FORM_URLENCODED);
        httpHeaders.put("X-Plex-Client-Identifier", "plex-on-the-go");
        httpHeaders.put("charset", "utf-8");

        List<NameValuePair> nameValuePairs = new ArrayList<>();

        nameValuePairs.add(new BasicNameValuePair("user[login]", userName));
        nameValuePairs.add(new BasicNameValuePair("user[password]", password));
        String response = HttpUtil.getInstance(false).postUrlEncoded(plexUrl, nameValuePairs, httpHeaders);
        AccessToken token= mapper.readValue(response, AccessToken.class);
        return token.getUser().getAuthentication_token();
    }

    public static List<Movie> exploreMovies(String baseUrl, Section mediaLibraries, String token, String user, boolean bypassSSL) throws HttpException, IOException {
        List<Movie> movies = new ArrayList();
        for (Directory directory : mediaLibraries.getMediaContainer().getDirectory()) {

            if (MediaType.MOVIE_TYPE.equals(directory.getType())) {
                String library = directory.getTitle();
                String moviesJson= HttpUtil.getInstance(bypassSSL).get(baseUrl + "/library/sections/" + directory.getKey().toString() + "/all", getPlexHttpHeaders(token));
                Section moviesSection = mapper.readValue(moviesJson, Section.class);

                for (Metadata metadata : moviesSection.getMediaContainer().getMetadata()) {

                    for (MediaPlex plexMedia : metadata.getMedia()) {
                        Movie movie = new Movie();
                        loadMediaInfo(movie, metadata, plexMedia, library, user);
                        movies.add(movie);
                    }
                }
            }
        }
        return movies;
    }

    public static List<Episode> exploreTvShows(String baseUrl, Section mediaLibraries, String token, String user, boolean bypassSSL) throws HttpException, IOException {
        List<Episode> episodesList = new ArrayList();
        Season season;
        Show show;
        for (Directory directory : mediaLibraries.getMediaContainer().getDirectory()) {
            if (MediaType.TV_SHOW_TYPE.equals(directory.getType())) {
                String library = directory.getTitle();
                String tvShowsJson = HttpUtil.getInstance(bypassSSL).get(baseUrl + "/library/sections/" + directory.getKey().toString() + "/all", getPlexHttpHeaders(token));
                Section tvshows = mapper.readValue(tvShowsJson, Section.class);
                for (Metadata showMetadata : tvshows.getMediaContainer().getMetadata()) {
                    show = parseShow(showMetadata);

                    String seasonsJson = HttpUtil.getInstance(bypassSSL).get(baseUrl + "/"+ showMetadata.getKey().toString(), getPlexHttpHeaders(token));
                    Section seasons = mapper.readValue(seasonsJson, Section.class);
                    for (Metadata seasonMedata : seasons.getMediaContainer().getMetadata()) {
                        season = new Season();
                        season.setSeasonNumber(seasonMedata.getIndex());
                        String episodesJson = HttpUtil.getInstance(bypassSSL).get(baseUrl + "/"+  seasonMedata.getKey().toString(), getPlexHttpHeaders(token));
                        Section episodes = mapper.readValue(episodesJson, Section.class);
                        for (Metadata episodeMetadata : episodes.getMediaContainer().getMetadata()) {

                            for (MediaPlex plexMedia : episodeMetadata.getMedia()) {
                                Episode episode = new Episode();
                                loadMediaInfo(episode, episodeMetadata, plexMedia, library, user);
                                episode.setSeason(season);
                                episode.setShow(show);
                                episode.setEpisode(episodeMetadata.getIndex());
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

    private static void loadMediaInfo(Media media, Metadata metadata, MediaPlex plexMedia, String library, String user) {
        media.setFileLocation(plexMedia.getPart().get(0).getFile());
        media.setRating(metadata.getRating());
        media.setSummary(metadata.getSummary());
        media.setTitle(metadata.getTitle());
        media.setYear(metadata.getYear());
        media.setUser(user);
        media.setLibrary(library);
        if (metadata.getOriginallyAvailableAt() != null) {
            media.setStartDate(LocalDate.parse(metadata.getOriginallyAvailableAt()));
        }

    }

    private static Map<String, String> getPlexHttpHeaders(String token){
        Map<String, String> httpHeaders = new HashMap();
        httpHeaders.put("Content-Type", "application/json;charset=UTF-8");
        httpHeaders.put("Accept", "application/json");
        httpHeaders.put("X-Plex-Token", token);
        return httpHeaders;
    }
}


