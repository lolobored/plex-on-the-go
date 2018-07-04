package org.lolobored.plex.elasticsearch.repository;

import org.lolobored.plex.elasticsearch.search.DataType;
import org.lolobored.plex.model.Media;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;
import java.util.Optional;

public interface DataTypeRepository extends ElasticsearchRepository<DataType, String> {

	Page<DataType> findByType(String type, Pageable pageable);

	@Query("{\n" +
		"  \"bool\": {\n" +
		"    \"must\": [\n" +
		"      {\n" +
		"        \"term\": {\n" +
		"          \"type.keyword\": \"?0\"\n" +
		"        }\n" +
		"      },\n" +
		"      {\n" +
		"        \"term\": {\n" +
		"          \"value.keyword\": \"?1\"\n" +
		"        }\n" +
		"      }\n" +
		"    ]\n" +
		"  }\n" +
		"}")
	Optional<DataType> findByTypeAndValue(String type, String value);
}
