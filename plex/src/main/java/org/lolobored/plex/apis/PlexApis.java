package org.lolobored.plex.apis;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.lolobored.objects.mediacontainer.Directory;
import org.lolobored.objects.mediacontainer.MediaType;
import org.lolobored.objects.mediacontainer.Section;
import org.lolobored.objects.metadata.Metadata;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.lolobored.http.HttpException;
import org.lolobored.http.HttpUtil;
import org.lolobored.plex.apis.token.AccessToken;

import java.io.IOException;
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

    public static Section fetchAllLibraries(String plexUrl, String token, boolean bypassSSL) throws HttpException, IOException {
        Map<String, String> httpHeaders = getPlexHttpHeaders(token);

        String librariesGet = HttpUtil.getInstance(bypassSSL).get(String.format("%s/library/sections", plexUrl), httpHeaders);
        Section libraries = mapper.readValue(librariesGet, Section.class);
        return libraries;
    }

    public static List<Section> fetchMovieLibrariesDetails(String plexUrl, Section allLibraries, String token, boolean bypassSSL) throws HttpException, IOException {
        List<Section> movies= new ArrayList<>();
        Map<String, String> httpHeaders = getPlexHttpHeaders(token);

        for (Directory directory : allLibraries.getMediaContainer().getDirectory()) {
            if (MediaType.MOVIE_TYPE.equals(directory.getType())) {
                String currentSection = HttpUtil.getInstance(bypassSSL).get(String.format("%s/library/sections/%s/all", plexUrl, directory.getKey().toString()), httpHeaders);
                Section movieSection = mapper.readValue(currentSection, Section.class);
                movies.add(movieSection);
            }
        }
        return movies;
    }

    public static List<Section> fetchTvshowsLibrariesDetails(String plexUrl, Section allLibraries, String token, boolean bypassSSL) throws HttpException, IOException {
        List<Section> tvshows= new ArrayList<>();
        Map<String, String> httpHeaders = getPlexHttpHeaders(token);

        for (Directory directory : allLibraries.getMediaContainer().getDirectory()) {
            if (MediaType.TV_SHOW_TYPE.equals(directory.getType())) {
                String currentSection = HttpUtil.getInstance(bypassSSL).get(String.format("%s/library/sections/%s/all", plexUrl, directory.getKey().toString()), httpHeaders);
                Section tvshowsSection = mapper.readValue(currentSection, Section.class);
                tvshows.add(tvshowsSection);
            }
        }
        return tvshows;
    }

//    public static List<Section> fetchTvshowSeasonList(String plexUrl, Section showSection, String token, boolean bypassSSL) throws HttpException, IOException {
//        List<Section> tvshowsSeasons= new ArrayList<>();
//        Map<String, String> httpHeaders = getPlexHttpHeaders(token);
//
//        for (Metadata showMetadata : showsMetadata) {
//            String seasonsSectionJson = HttpUtil.getInstance(bypassSSL).get(plexUrl + "/" + showMetadata.getKey().toString(), httpHeaders);
//            Section seasonsSection = mapper.
//            tvshowsSeasons.add(seasonsSection);
//        }
//        for (Directory directory : allLibraries.getMediaContainer().getDirectory()) {
//            if (MediaType.TV_SHOW_TYPE.equals(directory.getType())) {
//                String currentSection = HttpUtil.getInstance(bypassSSL).get(String.format("%s/library/sections/%s/all", plexUrl, directory.getKey().toString()), httpHeaders);
//                Section tvshowsSection = mapper.readValue(currentSection, Section.class);
//                tvshows.add(tvshowsSection);
//            }
//        }
//        return tvshows;
//    }

    private static Map<String, String> getPlexHttpHeaders(String token){
        Map<String, String> httpHeaders = new HashMap();
        httpHeaders.put("Content-Type", "application/json;charset=UTF-8");
        httpHeaders.put("Accept", "application/json");
        httpHeaders.put("X-Plex-Token", token);
        return httpHeaders;
    }
}


