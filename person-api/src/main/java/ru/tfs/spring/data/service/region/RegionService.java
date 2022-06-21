package ru.tfs.spring.data.service.region;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.tfs.spring.data.dto.region.RegionDto;
import ru.tfs.spring.data.entity.Region;
import ru.tfs.spring.data.repository.RegionRepository;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class RegionService {

    private final RegionRepository  repository;

    private final RegionMapper      mapper;

    @Transactional
    public Region createEntity(RegionDto dto) {
        return repository
                .findRegionByName(dto.getName())
                .orElseGet(() -> repository.save(mapper.regionDtoToRegion(dto)));
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
    public Long getIdByName(String name) {
        return repository.findRegionByName(name).map(Region::getId).orElse(null);
    }

    @Transactional(readOnly = true)
    @Named("cascadeUpdate")
    public Region cascadeUpdate(RegionDto dto) {
        Region region = repository.findById(dto.getId())
                .orElseThrow(() ->new EntityNotFoundException(String.format("Region с id %d не найден", dto.getId())));
        return mapper.cascadeUpdate(region, dto);
    }
}
