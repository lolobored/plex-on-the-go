package org.lolobored.plex.spring.services.impl;

import org.lolobored.http.HttpException;
import org.lolobored.plex.PlexService;
import org.lolobored.plex.model.Media;
import org.lolobored.plex.spring.models.Search;
import org.lolobored.plex.spring.models.User;
import org.lolobored.plex.spring.repository.UserRepository;
import org.lolobored.plex.spring.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PlexService plexService;


    @Override
    public List<String> getGenres() {
        List<User> users = repository.findAll();
        List<String> genreList= new ArrayList();
        List<Media> mediaList= getMovies();
        for (Media media: mediaList){
            for (String genre : media.getGenres()){
                if (!genreList.contains(genre)){
                    genreList.add(genre);
                }
            }
        }
        Collections.sort(genreList);
        return genreList;
    }

    @Override
    public List<Integer> getYears() {
        List<User> users = repository.findAll();
        List<Integer> yearList= new ArrayList();
        List<Media> mediaList= getMovies();
        for (Media media: mediaList){
            if (!yearList.contains(media.getYear())){
                yearList.add(media.getYear());
                }

        }
        Collections.sort(yearList);
        return yearList;
    }

    @Override
    public List<Media> getMovies() {
        List<User> users = repository.findAll();
        List<Media> mediaList= new ArrayList();
        for (User user : users){
            if (user.isPlexTokenValidated()){
                try {
                    mediaList.addAll(plexService.exploreMovies("http://lolobored.local:32400",
                            user.getPlexToken(),
                            user.getPlexLogin(),
                            false));
                } catch (HttpException | IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return mediaList;
    }

    @Override
    public List<Media> searchMovies(Search search) {
        List<Media> mediaList= getMovies();
        List<Media> result = new ArrayList<>();
        for (Media media : mediaList){
            boolean found = false;
            for (String mediaGenre: media.getGenres()){
                for (String searchGenre: search.getGenres()){
                    if (mediaGenre.equals(searchGenre)){
                        result.add(media);
                        found= true;
                        break;
                    }
                }
                if (found){
                    break;
                }
            }
        }
        return result;

    }
}
