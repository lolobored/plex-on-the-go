package org.lolobored.plex.spring.repository;

import org.lolobored.plex.spring.models.PendingConversion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConversionRepository extends JpaRepository<PendingConversion, String> {

	List<PendingConversion> findAllByDoneFalseOrderByCreationDateTime();

}
