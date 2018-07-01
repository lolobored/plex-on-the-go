package org.lolobored.plex.spring.services;

import org.lolobored.http.HttpException;
import org.lolobored.plex.exception.PlexException;
import org.lolobored.plex.spring.models.PlexUser;
import org.springframework.web.bind.annotation.RequestBody;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;

public interface PlexServiceRest {

	boolean plexLogin(String login, String password);

	List<PlexUser> getPlexUserDetails(@RequestBody PlexUser plexUser) throws HttpException, JAXBException, PlexException, IOException;

	void savePlexUsersDetails(@RequestBody PlexUser plexUser) throws HttpException, JAXBException, PlexException, IOException;

	List<PlexUser> getUsers();

	void deletePlexUser(String id);
}
