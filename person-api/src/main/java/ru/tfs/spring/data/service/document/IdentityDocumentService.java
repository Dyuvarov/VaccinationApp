package ru.tfs.spring.data.service.document;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tfs.spring.data.dto.document.IdentityDocumentCreateRequestDto;
import ru.tfs.spring.data.dto.document.IdentityDocumentDto;
import ru.tfs.spring.data.entity.IdentityDocument;
import ru.tfs.spring.data.repository.IdentityDocumentRepository;
import ru.tfs.spring.data.type.DocumentType;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IdentityDocumentService {

    private final IdentityDocumentMapper        mapper;

    private final IdentityDocumentRepository    repository;

    @Transactional
    public IdentityDocument createEntity(IdentityDocumentCreateRequestDto request, Long personId) {
        if (request.getMainDoc()) {
            validateMainDoc(personId);
        }
        return mapper.createRequestDtoToEntity(request);
    }

    public IdentityDocumentDto createDto(IdentityDocument entity) {
        return mapper.identityDocumentToIdentityDocumentDto(entity);
    }

    @Transactional(readOnly = true)
    protected void validateMainDoc(Long personId) {
        if (personId != null && repository.existsByMainDocIsTrueAndPersonId(personId)) {
            throw new IllegalArgumentException("Попытка добавить второй основной документ");
        }
    }

    @Transactional(readOnly = true)
    public Optional<IdentityDocumentDto> findByTypeAndNumber(DocumentType type, Integer number) {
        return repository
                    .findIdentityDocumentByDocumentTypeAndNumber(type, number)
                    .map(mapper::identityDocumentToIdentityDocumentDto);
    }

    @Transactional(readOnly = true)
    public IdentityDocument cascadeUpdate(IdentityDocumentDto dto) {
        IdentityDocument doc = repository.findById(dto.getId())
                .orElseThrow(() ->new EntityNotFoundException(String.format("IdentityDocument с id %d не найден", dto.getId())));
        return mapper.update(doc, dto);
    }
}
