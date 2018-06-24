package org.lolobored.plex.spring.repository;

import org.lolobored.plex.spring.models.Conversion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConversionRepository extends JpaRepository<Conversion, String> {

	List<Conversion> findAllByDoneFalseOrderByCreationDate();

}
