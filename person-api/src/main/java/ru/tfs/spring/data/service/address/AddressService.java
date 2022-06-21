package ru.tfs.spring.data.service.address;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tfs.spring.data.dto.address.AddressDto;
import ru.tfs.spring.data.entity.Address;
import ru.tfs.spring.data.repository.AddressRepository;
import ru.tfs.spring.data.service.region.RegionService;

import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.Set;

/** Сервис для работы с сущностью "Адрес" */
@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository repository;

    private final AddressMapper mapper;

    private final RegionService regionService;

    @Transactional(readOnly = true) //readonly т.к. сущность сохраняется каскадно с person
    public Address createEntity(AddressDto dto) {
        Address address = repository.findAddressByAllFields(
                regionService.getIdByName(dto.getRegion().getName()), dto.getCity(), dto.getStreet(), dto.getHouse(), dto.getFlat()
        );
        return address == null ? mapper.addressDtoToAddress(dto) : address;
    }

    public AddressDto createDto(Address entity) {
        return mapper.addressToAddressDto(entity);
    }

    @Named("cascadeUpdate")
    @Transactional(readOnly = true)
    public Address cascadeUpdate(AddressDto dto) {
        Address address = repository.findById(dto.getId())
                .orElseThrow(() ->new EntityNotFoundException(String.format("Address с id %d не найден", dto.getId())));
        return mapper.cascadeUpdate(address, dto);
    }

    /** Преобразование множества DTO в множество Entity */
    public Set<Address> addressDtoSetToAddressSet(Set<AddressDto> set) {
        if ( set == null ) {
            return null;
        }
        Set<Address> set1 = new HashSet<>();
        for ( AddressDto addressDto : set ) {
            if (addressDto.getId() == null) {
                set1.add(createEntity(addressDto));
            } else {
                set1.add( cascadeUpdate(addressDto ));
            }
        }
        return set1;
    }
}
