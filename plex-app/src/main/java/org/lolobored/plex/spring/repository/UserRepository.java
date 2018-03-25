package org.lolobored.plex.spring.repository;

import org.lolobored.plex.spring.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    public Optional<User> findByUserName(String userName);
}
