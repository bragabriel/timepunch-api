package io.github.bragabriel.timepunch_api.application.service;

import io.github.bragabriel.timepunch_api.application.chain.PunchClockChainHandler;
import io.github.bragabriel.timepunch_api.domain.entity.PunchClock;
import io.github.bragabriel.timepunch_api.domain.entity.User;
import io.github.bragabriel.timepunch_api.domain.repository.PunchClockRepository;
import io.github.bragabriel.timepunch_api.domain.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class PunchClockService {

	private final PunchClockRepository punchClockRepository;
	private final UserRepository userRepository;
	private final PunchClockChainHandler firstHandler;

	@Transactional
	public void registerPunchClock(final Long userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new IllegalArgumentException("User not found"));

		LocalDateTime registerTime = LocalDateTime.now();

		List<PunchClock> userPunches = punchClockRepository.findByUserIdAndPunchTime(userId, registerTime.toLocalDate());

		firstHandler.handle(user, userPunches, registerTime);

		PunchClock punchClock = new PunchClock();
		punchClock.setUser(user);
		punchClock.setPunchTime(registerTime);
		punchClockRepository.save(punchClock);
	}
}
