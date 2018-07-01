package org.lolobored.plex.spring.services.impl;

import org.lolobored.http.HttpException;
import org.lolobored.plex.PlexService;
import org.lolobored.plex.exception.PlexException;
import org.lolobored.plex.infrastructure.AccessTokens;
import org.lolobored.plex.infrastructure.MediaContainer;
import org.lolobored.plex.spring.models.PlexUser;
import org.lolobored.plex.spring.repository.PlexUsersRepository;
import org.lolobored.plex.spring.services.PlexServiceRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PlexServiceRestImpl implements PlexServiceRest {

	@Autowired
	PlexService plexService;

	@Autowired
	PlexUsersRepository plexUsersRepository;

	@Override
	public boolean plexLogin(String login, String password) {
		try {
			plexService.authenticate(login, password);

		} catch (HttpException | IOException e) {
			return false;
		}
		return true;
	}

	@Override
	public List<PlexUser> getUsers(){
		return plexUsersRepository.findAll();
	}

	@Override
	public void deletePlexUser(String id) {
		plexUsersRepository.deleteById(id);
	}

	@Override
	public void savePlexUsersDetails(PlexUser plexUser) throws HttpException, JAXBException, PlexException, IOException {
		List<PlexUser> plexUsers = getPlexUserDetails(plexUser);
		for (PlexUser user : plexUsers) {
			plexUsersRepository.save(user);
		}

	}

	@Override
	public List<PlexUser> getPlexUserDetails(PlexUser plexUser) throws IOException, HttpException, JAXBException, PlexException {
		String mainUserToken = plexService.authenticate(plexUser.getUsername(), plexUser.getPassword());
		List<PlexUser> plexUsers= new ArrayList<>();
		MediaContainer.Server server = plexService.retrieveMachineIdentifier(mainUserToken, plexUser.getMachineName());
		String serverToken = server.getAccessToken();
		String machineId = server.getMachineIdentifier();

		PlexUser mainUser= new PlexUser();
		mainUser.setMainUser(true);
		mainUser.setPassword(plexUser.getPassword());
		mainUser.setUsername(plexUser.getUsername());
		mainUser.setToken(mainUserToken);
		mainUser.setId(UUID.randomUUID().toString());
		mainUser.setMachineId(machineId);
		mainUser.setMachineName(plexUser.getMachineName());
		mainUser.setMachineToken(serverToken);
		plexUsers.add(mainUser);
		AccessTokens accessTokens= plexService.retrieveAccessTokens(serverToken, machineId);
		for (AccessTokens.AccessToken accessToken : accessTokens.getAccessToken()) {
			// consider it a user of the plex library
			if (accessToken.getDevice()== null){
				if (!plexUser.getUsername().equalsIgnoreCase(accessToken.getUsername())) {
					PlexUser newUser= new PlexUser();
					newUser.setMachineId(machineId);
					newUser.setToken(serverToken);
					newUser.setMainUser(false);
					newUser.setPassword(null);
					newUser.setUsername(accessToken.getUsername());
					newUser.setToken(accessToken.getToken());
					newUser.setId(UUID.randomUUID().toString());
					mainUser.setMachineId(machineId);
					newUser.setMachineName(plexUser.getMachineName());
					newUser.setMachineToken(serverToken);
					plexUsers.add(newUser);
				}
			}
		}
		return plexUsers;
	}

}
