package org.lolobored.plex;

import org.lolobored.http.HttpException;
import org.lolobored.plex.exception.PlexException;
import org.lolobored.plex.infrastructure.AccessTokens;
import org.lolobored.plex.infrastructure.MediaContainer;
import org.lolobored.plex.model.Media;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;

public interface PlexService {

	/**
	 * Authentication is done on the plex website (https://plex.tv/users/sign_in.json)
	 * It requires obviously a valid username/password
	 * That will essentially give back a token
	 *
	 * @param userName the valid user name on https://plex.tv
	 * @param password the associated password on https://plex.tv
	 * @return the token
	 * @throws HttpException
	 * @throws IOException
	 */
	String authenticate(String userName, String password) throws HttpException, IOException;

	/**
	 * Explore the movies libraries and returns a List of Media (later on to be inserted into Elastic Search)
	 *
	 * @param baseUrl   the baseUrl for plex (ie http://lolobored.local:32400)
	 * @param token     the token retrieved during the authentication phase
	 * @param user      the user associated to that token
	 * @param bypassSSL whether or not to bypass SSL check (set to true for self-generated certificates)
	 * @return the list of Movies
	 * @throws HttpException
	 * @throws IOException
	 */
	List<Media> exploreMovies(String baseUrl, String token, String user, boolean bypassSSL) throws HttpException, IOException;

	/**
	 * Explore the tvshows libraries and returns a List of Media (later on to be inserted into Elastic Search)
	 *
	 * @param baseUrl   the baseUrl for plex (ie http://lolobored.local:32400)
	 * @param token     the token retrieved during the authentication phase
	 * @param user      the user associated to that token
	 * @param bypassSSL whether or not to bypass SSL check (set to true for self-generated certificates)
	 * @return the list of TVShows
	 * @throws HttpException
	 * @throws IOException
	 */
	List<Media> exploreTvShows(String baseUrl, String token, String user, boolean bypassSSL) throws HttpException, IOException;

	MediaContainer.Server retrieveMachineIdentifier(String mainUserToken, String name) throws HttpException, JAXBException, PlexException;

	AccessTokens retrieveAccessTokens(String serverToken, String machineId) throws HttpException, JAXBException;
}
