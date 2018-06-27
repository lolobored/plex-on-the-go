package org.lolobored.plex.spring.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.lolobored.plex.model.Media;
import org.lolobored.plex.spring.models.RunningConversion;
import org.lolobored.plex.spring.services.ConversionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RestController
public class ConversionController {

	@Autowired
	ConversionService conversionService;

	@PostMapping(path = "/plex-backend/conversions", consumes = "application/json")
	@PreAuthorize("isAuthenticated()")
	public List<RunningConversion> getPendingConversions() throws IOException {

		return conversionService.getPendingConversions();
	}

	@PostMapping(path = "/plex-backend/conversions/add", consumes = "application/json")
	@PreAuthorize("isAuthenticated()")
	public void addConversion(@RequestBody Media media, Principal principal) throws JsonProcessingException {

		conversionService.addConversion(media, principal.getName());
	}
}
