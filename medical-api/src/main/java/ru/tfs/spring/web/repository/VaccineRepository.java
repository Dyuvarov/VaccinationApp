package ru.tfs.spring.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tfs.spring.web.entity.Vaccine;

import java.util.Optional;

@Repository
public interface VaccineRepository extends JpaRepository<Vaccine, Long> {
	Optional<Vaccine> findVaccineByName(String name);
}
