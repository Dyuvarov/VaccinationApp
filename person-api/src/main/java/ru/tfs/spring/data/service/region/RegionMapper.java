package ru.tfs.spring.data.service.region;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.tfs.spring.data.dto.region.RegionDto;
import ru.tfs.spring.data.entity.Region;

@Mapper(componentModel = "spring")
public interface RegionMapper  {
	Region regionDtoToRegion(RegionDto dto);

	@Mapping(target = "id", ignore = true)
	Region cascadeUpdate(@MappingTarget Region region, RegionDto dto);
}
