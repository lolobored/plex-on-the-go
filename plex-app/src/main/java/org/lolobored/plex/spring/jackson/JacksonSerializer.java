package org.lolobored.plex.spring.jackson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.node.TextNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.lolobored.plex.model.Media;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

@JsonComponent
public class JacksonSerializer {

	public static class UserJsonSerializer
		extends JsonSerializer<Media> {

		@Override
		public void serialize(Media media, JsonGenerator jsonGenerator,
													SerializerProvider serializerProvider) throws IOException,
			JsonProcessingException {

			ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule())
				.setSerializationInclusion(JsonInclude.Include.NON_NULL);

			mapper.writeValueAsString(media);

		}


	}

	public static class UserJsonDeserializer
		extends JsonDeserializer<Media> {

		@Override
		public Media deserialize(JsonParser jsonParser,
														DeserializationContext deserializationContext)
			throws IOException, JsonProcessingException {
			ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule())
				.setSerializationInclusion(JsonInclude.Include.NON_NULL);


			return mapper.readValue(jsonParser.getValueAsString(), Media.class);
		}
	}
}
