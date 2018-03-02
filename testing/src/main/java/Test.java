import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.lolobored.http.HttpException;
import org.lolobored.http.HttpUtil;
import org.lolobored.plex.apis.PlexApis;
import org.lolobored.plex.elasticsearch.ElasticSearch;
import org.lolobored.plex.elasticsearch.objects.Result;
import org.lolobored.plex.model.Episode;
import org.lolobored.plex.model.Movie;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class Test {

	public static void main(String[] args) throws IOException, HttpException {

		Properties properties = new Properties();
		String respath = "test.properties";

		properties.load(Test.class.getResourceAsStream(respath));

		String token = PlexApis.authenticate(properties.getProperty("username"), properties.getProperty("password"));

		List<Movie> movies = PlexApis.exploreMovies(properties.getProperty("plexUrl"), token, properties.getProperty("username"), true);
		List<Episode> episodes = PlexApis.exploreTvShows(properties.getProperty("plexUrl"), token, properties.getProperty("username"), true);

		for (Movie movie: movies){
			ElasticSearch.insertMedia(properties.getProperty("elasticSearchUrl"), movie, true);
		}
		for (Episode episode: episodes){
			ElasticSearch.insertMedia(properties.getProperty("elasticSearchUrl"), episode, true);
		}

		String request = "{\n" +
				"\n" +
				"\"query\" : {\n" +
				"        \"term\" : { \"show.showTitle\": \"24\"}\n" +
				"    }, \"sort\" : [{ \"season.seasonNumber\" : \"asc\", \"episode\" : \"asc\" }]\n" +
				"}";
		Map<String, String> header = new HashMap<>();
		header.put("Content-Type", "application/json");
		String answer= HttpUtil.getInstance(false).post("http://localhost:9200/media/_search", request, header);
		ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
		Result result= mapper.readValue(answer, Result.class);
		int i=0;
	}
}
