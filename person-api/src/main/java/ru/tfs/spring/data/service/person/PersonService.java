package ru.tfs.spring.data.service.person;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tfs.spring.data.dto.document.IdentityDocumentCreateRequestDto;
import ru.tfs.spring.data.dto.document.IdentityDocumentDto;
import ru.tfs.spring.data.dto.person.PersonCreateRequestDto;
import ru.tfs.spring.data.dto.person.PersonDto;
import ru.tfs.spring.data.dto.person.PersonFullDto;
import ru.tfs.spring.data.dto.person.PersonJournalDto;
import ru.tfs.spring.data.entity.Contact;
import ru.tfs.spring.data.entity.IdentityDocument;
import ru.tfs.spring.data.entity.Person;
import ru.tfs.spring.data.repository.PersonRepository;
import ru.tfs.spring.data.service.address.AddressService;
import ru.tfs.spring.data.service.contact.ContactService;
import ru.tfs.spring.data.service.document.IdentityDocumentService;
import ru.tfs.spring.data.type.ContactType;
import ru.tfs.spring.data.type.DocumentType;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;


/** Сервис для работы с сущностью "Персона" */
@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository          repository;

    private final PersonMapper              mapper;

    private final IdentityDocumentService   documentService;

    private final ContactService            contactService;

    private final AddressService            addressService;

    @Transactional
    public PersonFullDto create(PersonCreateRequestDto request) {
        long mainDocCount = request.getIdentityDocuments()
                .stream()
                .filter(IdentityDocumentCreateRequestDto::getMainDoc)
                .count();
        if (mainDocCount > 1) {
            throw new IllegalArgumentException("Основной документ может быть только один");
        }

        PersonDto dto = mapper.createRequestToDto(request);
        Person person = mapper.dtoToEntity(dto);
        person.setIdentityDocuments(
                request.getIdentityDocuments()
                        .stream()
                        .map(doc -> documentService.createEntity(doc, person.getId()))
                        .collect(Collectors.toSet())
        );
        person.setContacts(
                request.getContacts()
                        .stream()
                        .map(contactService::createEntity)
                        .collect(Collectors.toSet())
        );

        return mapper.personToFullDtoByPersonDto(repository.save(person));
    }

    @Transactional
    public PersonFullDto update(PersonFullDto dto) {
        Person person = findById(dto.getId());
        mapper.cascadeUpdate(person, dto);
        repository.save(person);
        return mapper.personToFullDtoByPersonDto(person);
    }

    /** Возвращает полную информацию о персоне */
    @Transactional(readOnly = true)
    public PersonFullDto personInfo(Long id) {
        Person person = findById(id);
        return  mapper.personToFullDtoByPersonDto(person);
    }

    @Transactional(readOnly = true)
    public PersonJournalDto personInfoByPassport(Integer passport) {
       IdentityDocumentDto documentDto = documentService.findByTypeAndNumber(DocumentType.RUSSIAN_PASSPORT, passport)
               .orElseThrow(() ->new EntityNotFoundException(String.format("Не найдена информация по паспорту %d", passport)));
       return getJournalDto(findById(documentDto.getPersonId()));
    }

    @Transactional(readOnly = true)
    public Person findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->new EntityNotFoundException(String.format("Person с id %d не найден", id)));
    }

    /** Возвращает список персон для постраничного отображения */
    @Transactional(readOnly = true)
    public List<PersonJournalDto> personJournal(Integer size, Integer page, String regionName) {
        PageRequest pageable = PageRequest.of(page, size);
        List<Person> foundPersons;
        if (regionName != null) {
            foundPersons = repository.findNotHiddenWithPageableByRegion(regionName, pageable);
        } else {
            foundPersons = repository.findNotHiddenWithPageable(pageable);
        }

        List<PersonJournalDto> result = foundPersons
                .stream()
                .map(this::getJournalDto).collect(Collectors.toList());
        return result;
    }

    private PersonJournalDto getJournalDto(Person person) {
        PersonJournalDto dto = mapper.entityToJournalDto(person);
        if (person.getContacts() != null) {
            Contact contact = person.getContacts()
                    .stream()
                    .filter(cnt -> ContactType.PHONE_NUMBER == cnt.getType())
                    .findAny()
                    .orElse(null);
            dto.setPhoneNumber(contact == null ? null : contact.getValue());
        }
        if (person.getIdentityDocuments() != null) {
            IdentityDocument document = person.getIdentityDocuments()
                    .stream()
                    .filter(IdentityDocument::getMainDoc)
                    .findAny()
                    .orElse(null);
            dto.setMainDocument(document == null ? null : documentService.createDto(document).toString());
        }
        if (person.getRegistryAddress() != null) {
            dto.setRegistryAddress(addressService.createDto(person.getRegistryAddress()).toString());
        }
        return dto;
    }

    /** Проверка связки ФИО + паспорт */
    @Transactional(readOnly = true)
    public Boolean verify(String firstName, String lastName, String patronymic, Integer passportNumber) {
        return repository.findByFIOAndDocumentNumber(firstName, lastName, patronymic, passportNumber).isPresent();
    }
}
