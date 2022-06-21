package ru.tfs.spring.web.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tfs.spring.web.entity.Vaccine;
import ru.tfs.spring.web.repository.VaccineRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class VaccineService {
	private final VaccineRepository repository;

	@Transactional
	public Vaccine create(Vaccine vaccine) {
		return repository.save(vaccine);
	}

	@Transactional
	public Vaccine findByNameOrCreate(String name) {
		Optional<Vaccine> optionalVaccine = repository.findVaccineByName(name);
		if (optionalVaccine.isEmpty()) {
			Vaccine vaccine = new Vaccine();
			vaccine.setName(name);
			return create(vaccine);
		}
		return optionalVaccine.get();
	}
}
