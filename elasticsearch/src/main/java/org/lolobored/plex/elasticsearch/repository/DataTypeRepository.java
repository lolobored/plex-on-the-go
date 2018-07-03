package org.lolobored.plex.elasticsearch.repository;

import org.lolobored.plex.elasticsearch.search.DataType;
import org.lolobored.plex.model.Media;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;
import java.util.Optional;

public interface DataTypeRepository extends ElasticsearchRepository<DataType, String> {

	Page<DataType> findByType(String type, Pageable pageable);

	Optional<DataType> findByTypeEqualsAndValueEquals(String type, String value);
}
