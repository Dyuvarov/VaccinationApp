package ru.tfs.spring.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tfs.spring.web.entity.Vaccination;

import java.util.List;
import java.util.Optional;

@Repository
public interface VaccinationRepository extends JpaRepository<Vaccination, Long> {

	Optional<Vaccination> findTopByDocumentOrderByDate(Integer document);

	/** Найти все неотправленные в kafka акцинации */
	List<Vaccination> findVaccinationByHandleDateIsNull();
}
