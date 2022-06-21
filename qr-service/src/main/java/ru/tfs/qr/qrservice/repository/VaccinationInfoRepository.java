package ru.tfs.qr.qrservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tfs.qr.qrservice.entity.VaccinationInfo;

import java.util.Optional;

@Repository
public interface VaccinationInfoRepository extends JpaRepository<VaccinationInfo, Long> {
	Optional<VaccinationInfo> findVaccinationInfoByUuid(String uuid);

	Optional<VaccinationInfo> findTopByDocumentOrderByDateDesc(Integer document);

	Optional<VaccinationInfo> findVaccinationInfoByQrCodeCode(String code);
}
