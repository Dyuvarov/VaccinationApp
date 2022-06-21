package ru.tfs.spring.data.service.contact;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tfs.spring.data.dto.contact.ContactCreateRequestDto;
import ru.tfs.spring.data.dto.contact.ContactDto;
import ru.tfs.spring.data.entity.Contact;
import ru.tfs.spring.data.repository.ContactRepository;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class ContactService {
	private final ContactRepository	repository;

	private final ContactMapper		mapper;

	public Contact createEntity(ContactCreateRequestDto request) {
		return mapper.createRequestToDto(request);
	}

	@Transactional(readOnly = true)
	public Contact cascadeUpdate(ContactDto dto) {
		Contact contact = repository.findById(dto.getId())
				.orElseThrow(() ->new EntityNotFoundException(String.format("Contact с id %d не найден", dto.getId())));
		return mapper.update(contact, dto);
	}
}
