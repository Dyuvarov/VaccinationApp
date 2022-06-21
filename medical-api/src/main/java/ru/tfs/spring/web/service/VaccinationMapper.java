package ru.tfs.spring.web.service;

import org.mapstruct.Mapper;
import ru.tfs.commontypes.kafka.VaccinationTransportDto;
import ru.tfs.spring.web.entity.Vaccination;

@Mapper(componentModel = "spring")
public interface VaccinationMapper {
	VaccinationTransportDto vaccinationToTransportDto(Vaccination vaccination);
}
