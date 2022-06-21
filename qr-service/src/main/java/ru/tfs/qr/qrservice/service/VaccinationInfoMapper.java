package ru.tfs.qr.qrservice.service;

import org.mapstruct.Mapper;
import ru.tfs.commontypes.kafka.VaccinationTransportDto;
import ru.tfs.qr.qrservice.entity.VaccinationInfo;

@Mapper(componentModel = "spring")
public interface VaccinationInfoMapper {
	VaccinationInfo vaccinationTransportDtoToVaccinationInfo(VaccinationTransportDto transportDto);
}
