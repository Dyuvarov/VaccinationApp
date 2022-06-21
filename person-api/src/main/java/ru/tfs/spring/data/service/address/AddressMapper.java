package ru.tfs.spring.data.service.address;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.tfs.spring.data.dto.address.AddressCreateRequestDto;
import ru.tfs.spring.data.dto.address.AddressDto;
import ru.tfs.spring.data.entity.Address;
import ru.tfs.spring.data.service.region.RegionService;

@Mapper(componentModel = "spring", uses = {RegionService.class})
public interface AddressMapper {
	Address addressDtoToAddress(AddressDto dto);

	AddressDto addressCreateRequestToAddressDto(AddressCreateRequestDto dto);

	AddressDto addressToAddressDto(Address address);

	@Mapping(target = "region", qualifiedByName = "cascadeUpdate")
	Address cascadeUpdate(@MappingTarget Address address, AddressDto dto);
}
