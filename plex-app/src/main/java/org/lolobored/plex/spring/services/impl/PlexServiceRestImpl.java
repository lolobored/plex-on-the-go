package org.lolobored.plex.spring.services.impl;

import org.lolobored.http.HttpException;
import org.lolobored.plex.PlexService;
import org.lolobored.plex.spring.services.PlexServiceRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class PlexServiceRestImpl implements PlexServiceRest {

	@Autowired
	private PlexService plexService;

	@Override
	public boolean plexLogin(String login, String password) {
		try {
			plexService.authenticate(login, password);

		} catch (HttpException | IOException e) {
			return false;
		}
		return true;
	}

}
