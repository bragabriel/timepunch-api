package io.github.bragabriel.timepunch_api.domain.mapper;

import io.github.bragabriel.timepunch_api.application.dto.PunchClockResponse;
import io.github.bragabriel.timepunch_api.domain.entity.PunchClock;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface PunchClockMapper {

	@Mapping(source = "user.name", target = "name")
	@Mapping(source = "punchTime", target = "timePunch")
	PunchClockResponse punchClockToResponse(PunchClock punchClock);

	@InheritInverseConfiguration
	@Mapping(target = "id", ignore = true)
	PunchClock punchClockResponseToPunchClock(PunchClockResponse punchClockResponse);

	@AfterMapping
	default void afterMapping(@MappingTarget PunchClockResponse response){
		System.out.println("after mapping");
	}

}

