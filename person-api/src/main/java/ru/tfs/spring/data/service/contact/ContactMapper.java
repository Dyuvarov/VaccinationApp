package ru.tfs.spring.data.service.contact;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.tfs.spring.data.dto.contact.ContactCreateRequestDto;
import ru.tfs.spring.data.dto.contact.ContactDto;
import ru.tfs.spring.data.entity.Contact;

@Mapper(componentModel = "spring")
public interface ContactMapper {
	Contact createRequestToDto(ContactCreateRequestDto request);

	@Mapping(target = "id", ignore = true)
	Contact update(@MappingTarget Contact contact, ContactDto dto);
}
