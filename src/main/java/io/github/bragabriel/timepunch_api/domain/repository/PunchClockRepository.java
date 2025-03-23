package io.github.bragabriel.timepunch_api.domain.repository;

import io.github.bragabriel.timepunch_api.domain.entity.PunchClock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PunchClockRepository extends JpaRepository<PunchClock, Long> {
	List<PunchClock> findByUserIdAndDate(Long userId, LocalDate date);
}
