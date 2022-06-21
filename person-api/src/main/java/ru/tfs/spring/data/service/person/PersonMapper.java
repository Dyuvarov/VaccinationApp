package ru.tfs.spring.data.service.person;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.tfs.spring.data.dto.person.PersonCreateRequestDto;
import ru.tfs.spring.data.dto.person.PersonDto;
import ru.tfs.spring.data.dto.person.PersonFullDto;
import ru.tfs.spring.data.dto.person.PersonJournalDto;
import ru.tfs.spring.data.entity.Person;
import ru.tfs.spring.data.service.address.AddressService;
import ru.tfs.spring.data.service.contact.ContactMapper;
import ru.tfs.spring.data.service.contact.ContactService;
import ru.tfs.spring.data.service.document.IdentityDocumentService;

@Mapper(componentModel = "spring", uses = {AddressService.class, IdentityDocumentService.class,
                                            ContactService.class, ContactMapper.class})
public interface PersonMapper {
    PersonFullDto personToFullDtoByPersonDto(Person person);

    @Mapping(target = "registryAddress", ignore = true)
    PersonJournalDto entityToJournalDto(Person person);

    PersonDto createRequestToDto(PersonCreateRequestDto createRequest);

    @Mapping(target = "registryAddress", qualifiedByName = "cascadeUpdate")
    @Mapping(target = "id", ignore = true)
    Person cascadeUpdate(@MappingTarget Person person, PersonFullDto dto);

    Person dtoToEntity(PersonDto dto);

}
