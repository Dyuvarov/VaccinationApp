package ru.tfs.spring.data.service.document;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.tfs.spring.data.dto.document.IdentityDocumentCreateRequestDto;
import ru.tfs.spring.data.dto.document.IdentityDocumentDto;
import ru.tfs.spring.data.entity.IdentityDocument;


@Mapper(componentModel = "spring")
public interface IdentityDocumentMapper  {
	IdentityDocument createRequestDtoToEntity(IdentityDocumentCreateRequestDto request);

	@Mapping(target = "personId", source = "person.id")
	IdentityDocumentDto identityDocumentToIdentityDocumentDto(IdentityDocument entity);

	@Mapping(target = "id", ignore = true)
	IdentityDocument update(@MappingTarget IdentityDocument doc, IdentityDocumentDto dto);
}
