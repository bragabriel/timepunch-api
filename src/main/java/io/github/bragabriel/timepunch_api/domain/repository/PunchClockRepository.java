package io.github.bragabriel.timepunch_api.domain.repository;

import io.github.bragabriel.timepunch_api.domain.entity.PunchClock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PunchClockRepository extends JpaRepository<PunchClock, Long> {

	@Query("SELECT p FROM PunchClock p WHERE p.user.id = :userId AND CAST(p.punchTime AS DATE) = :date")
	List<PunchClock> findByUserIdAndPunchDate(@Param("userId") Long userId, @Param("date") LocalDate date);
}
