package org.lolobored.plex.spring.models;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.lolobored.plex.model.Media;

import javax.persistence.*;
import java.io.IOException;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "conversion")
public class Conversion {

	@Id
	@Column
	private String id;
	@Column(columnDefinition="clob")
	@Lob
	private String media;
	@Column
	private boolean done= false;
	@Column
	private LocalDateTime creationDate;
	@Column
	private String userName;

	public void setMedia(Media mediaObj) throws JsonProcessingException {
		ObjectMapper mapper= new ObjectMapper();
		media= mapper.writeValueAsString(mediaObj);
	}

	public Media getMediaAsObject() throws IOException {
		ObjectMapper mapper= new ObjectMapper();
		return mapper.readValue(media, Media.class);
	}
}
