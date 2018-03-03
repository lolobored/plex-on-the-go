import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.lolobored.http.HttpException;
import org.lolobored.plex.apis.PlexApis;
import org.lolobored.plex.elasticsearch.ElasticSearch;
import org.lolobored.plex.elasticsearch.filters.Filters;
import org.lolobored.plex.elasticsearch.query.Bool;
import org.lolobored.plex.elasticsearch.query.Query;
import org.lolobored.plex.elasticsearch.query.Search;
import org.lolobored.plex.model.Media;

import java.io.IOException;
import java.util.*;

public class Test {

	public static void main(String[] args) throws IOException, HttpException {
		ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		Properties properties = new Properties();
		String respath = "test.properties";

		properties.load(Test.class.getResourceAsStream(respath));




		String token = PlexApis.authenticate(properties.getProperty("username"), properties.getProperty("password"));
		ElasticSearch.deleteMedia(properties.getProperty("elasticSearchUrl"), search, true);

		List<Media> movies = PlexApis.exploreMovies(properties.getProperty("plexUrl"), token, properties.getProperty("username"), true);
		List<Media> episodes = PlexApis.exploreTvShows(properties.getProperty("plexUrl"), token, properties.getProperty("username"), true);



		for (Media movie: movies){
			ElasticSearch.insertMedia(properties.getProperty("elasticSearchUrl"), movie, true);
		}
		for (Media episode: episodes){
			ElasticSearch.insertMedia(properties.getProperty("elasticSearchUrl"), episode, true);
		}

		/*String request = "{\n" +
				"\n" +
				"\"query\" : {\n" +
				"        \"term\" : { \"show.showTitle\": \"24\"}\n" +
				"    }, \"sort\" : [{ \"season.seasonNumber\" : \"asc\", \"episode\" : \"asc\" }]\n" +
				"}";
		Map<String, String> header = new HashMap<>();
		header.put("Content-Type", "application/json");
		String answer= HttpUtil.getInstance(false).post(properties.getProperty("elasticSearchUrl")+"/media/_search", request, header);

		Result result= mapper.readValue(answer, Result.class);*/

		ElasticSearch.getMoviesPerUser(properties.getProperty("elasticSearchUrl"),
				properties.getProperty("username"),
				20,
				200,
				true);

	}
}
