package org.lolobored.plex.elasticsearch.repository;

import org.lolobored.plex.model.Media;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface MediaRepository extends ElasticsearchRepository<Media, String> {

	Media findByUserAndPlexId(String user, String plexId);

	Page<Media> findByUserAndType(String user, String type, Pageable pageable);

	Page<Media> findByUserAndTypeAndGenresInAndYearGreaterThanEqualAndYearLessThanEqual(String user, String type,
																																											List<String> genres, Integer startYear, Integer endYear, Pageable pageable);

	Page<Media> findByUserAndTypeAndShowInAndYearGreaterThanEqualAndYearLessThanEqual(String user, String type,
																																										List<String> genres, Integer startYear, Integer endYear, Pageable pageable);

	@Query("{\n" +
		"  \"bool\": {\n" +
		"    \"must\": [\n" +
		"      {\n" +
		"        \"terms\": {\n" +
		"          \"show.showTitle.keyword\": [\n" +
		"            \"?0\"\n" +
		"          ]\n" +
		"        }\n" +
		"      },\n" +
		"      {\n" +
		"        \"term\": {\n" +
		"          \"user.keyword\": \"?1\"\n" +
		"        }\n" +
		"      }\n" +
		"    ]\n" +
		"  }\n" +
		"}")
	Page<Media> findTvShowByUserAndShow(String shows, String user, Pageable pageable);

}
