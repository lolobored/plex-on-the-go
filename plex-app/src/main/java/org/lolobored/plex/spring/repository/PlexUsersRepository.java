package org.lolobored.plex.spring.repository;


import org.lolobored.plex.spring.models.PlexUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlexUsersRepository extends JpaRepository<PlexUser, String> {

}
