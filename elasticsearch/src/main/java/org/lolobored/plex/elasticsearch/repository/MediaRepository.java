package org.lolobored.plex.elasticsearch.repository;

import org.lolobored.plex.model.Media;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface MediaRepository extends ElasticsearchRepository<Media, String> {

	Media findByUserAndPlexId(String user, String plexId);

	Page<Media> findByUser(String user, Pageable pageable);

	Page<Media> findByUserAndGenresInAndYearGreaterThanEqualAndYearLessThanEqual(String user, List<String> genres, Integer startYear, Integer endYear, Pageable pageable);
}
