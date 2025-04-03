package io.github.bragabriel.timepunch_api.domain.repository;

import io.github.bragabriel.timepunch_api.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
