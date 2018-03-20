package org.lolobored.plex.elasticsearch.repository;

import org.lolobored.plex.model.Media;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface MediaRepository extends ElasticsearchRepository<Media, String> {

	Media findByUserAndPlexId(String user, String plexId);
}
