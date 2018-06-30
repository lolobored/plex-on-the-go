package org.lolobored.plex.spring.repository;

import org.lolobored.plex.spring.models.Converted;
import org.lolobored.plex.spring.models.PendingConversion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConvertedRepository extends JpaRepository<Converted, String> {



}
