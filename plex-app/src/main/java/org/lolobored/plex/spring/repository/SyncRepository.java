package org.lolobored.plex.spring.repository;

import org.lolobored.plex.spring.models.Sync;
import org.lolobored.plex.spring.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SyncRepository extends JpaRepository<Sync, Integer> {


}
