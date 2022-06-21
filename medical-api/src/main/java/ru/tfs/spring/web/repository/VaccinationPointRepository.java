package ru.tfs.spring.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tfs.spring.web.entity.VaccinationPoint;

import java.util.Optional;

@Repository
public interface VaccinationPointRepository extends JpaRepository<VaccinationPoint, Long> {
	Optional<VaccinationPoint> findVaccinationPointByUuid(String uuid);
}
