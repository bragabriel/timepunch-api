package io.github.bragabriel.timepunch_api.application.service;

import io.github.bragabriel.timepunch_api.application.chain.PunchClockChainHandler;
import io.github.bragabriel.timepunch_api.application.dto.PunchClockResponse;
import io.github.bragabriel.timepunch_api.application.dto.WorkedHoursResponse;
import io.github.bragabriel.timepunch_api.domain.entity.PunchClock;
import io.github.bragabriel.timepunch_api.domain.entity.User;
import io.github.bragabriel.timepunch_api.domain.exception.NoRecordsFoundException;
import io.github.bragabriel.timepunch_api.domain.exception.UserNotFoundException;
import io.github.bragabriel.timepunch_api.domain.repository.PunchClockRepository;
import io.github.bragabriel.timepunch_api.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PunchClockService {

	private final PunchClockRepository punchClockRepository;
	private final UserRepository userRepository;
	private final PunchClockChainHandler firstHandler;

	public PunchClockService(
			final PunchClockRepository punchClockRepository,
			final UserRepository userRepository,
			@Qualifier("punchClockHandler") PunchClockChainHandler firstHandler) {
		this.punchClockRepository = punchClockRepository;
		this.userRepository = userRepository;
		this.firstHandler = firstHandler;
	}

	@Transactional
	public PunchClockResponse registerPunchClock(final Long userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException(userId));

		LocalDateTime registerTime = LocalDateTime.now();

		List<PunchClock> userPunches =
				punchClockRepository.findByUserIdAndPunchDate(userId, registerTime.toLocalDate());

		firstHandler.handle(user, userPunches, registerTime);

		PunchClock punchClock = new PunchClock();
		punchClock.setUser(user);
		punchClock.setPunchTime(registerTime);
		punchClockRepository.save(punchClock);

		return new PunchClockResponse(userId, user.getName(), registerTime);
	}

	public WorkedHoursResponse getWorkedHours(final Long userId, final LocalDate date) {
		List<PunchClock> punchClockList = punchClockRepository.findByUserIdAndPunchDate(userId, date);

		if (punchClockList.isEmpty()) {
			throw new NoRecordsFoundException(userId, date);
		}

		Duration totalWorked = Duration.ZERO;

		int punchCount = punchClockList.size();

		totalWorked = calculateWorkedHours(punchCount, totalWorked, punchClockList);
		String formattedWorkedHours = formatDuration(totalWorked);

		return new WorkedHoursResponse(formattedWorkedHours);
	}

	private Duration calculateWorkedHours(final int punchCount, Duration totalWorked,
										  final List<PunchClock> punchClockList) {
		if (punchCount == 1) {
			totalWorked = calculateSinglePunch(punchClockList.getFirst());
		} else if (punchCount == 2) {
			totalWorked = calculateTwoPunches(punchClockList);
		} else if (punchCount == 3) {
			totalWorked = calculateTwoPunches(punchClockList.subList(0, 2));
			totalWorked = totalWorked.plus(calculateSinglePunch(punchClockList.get(2)));
		} else if (punchCount == 4) {
			totalWorked = calculateTwoPunches(punchClockList.subList(0, 2));
			totalWorked = totalWorked.plus(calculateTwoPunches(punchClockList.subList(2, 4)));
		}
		return totalWorked;
	}

	private Duration calculateSinglePunch(PunchClock punchClock) {
		LocalDateTime start = punchClock.getPunchTime();
		LocalDateTime now = LocalDateTime.now();
		return Duration.between(start, now);
	}

	private Duration calculateTwoPunches(List<PunchClock> punchClockList) {
		LocalDateTime start = punchClockList.get(0).getPunchTime();
		LocalDateTime end = punchClockList.get(1).getPunchTime();
		return Duration.between(start, end);
	}

	private String formatDuration(Duration duration) {
		long hours = duration.toHours();
		long minutes = duration.toMinutesPart();
		return String.format("%02d:%02d", hours, minutes);
	}
}
