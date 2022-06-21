package ru.tfs.spring.web.service.importservices;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.tfs.spring.web.client.PersonFeignClient;
import ru.tfs.spring.web.dto.VaccinationImportDto;
import ru.tfs.spring.web.entity.VaccinationPoint;
import ru.tfs.spring.web.entity.Vaccine;
import ru.tfs.spring.web.service.VaccinationPointService;
import ru.tfs.spring.web.service.VaccinationService;
import ru.tfs.spring.web.service.VaccineService;

import java.util.*;

@Service
@AllArgsConstructor
@Slf4j
public class ImportService {

	private final VaccinationCSVMapper		csvMapper;

	private final VaccineService			vaccineService;

	private final VaccinationPointService	pointService;

	private final VaccinationService		vaccinationService;

	private final PersonFeignClient			personClient;

	@SneakyThrows
	public ResponseEntity<List<String>> processCSV(MultipartFile csvFile) {
		log.info("Начат импорт файла " + csvFile.getName());
		List<String> errorList = new LinkedList<>(); //список для хранения ошибок, возникших во время импорта
		Set<VaccinationImportDto> importSet = csvMapper.processMultipartFile(csvFile, errorList);
		if (errorList.isEmpty()) {
			deleteNotValid(importSet, errorList);
			importAll(importSet);
		}
		log.info("Завершен импорт файла " + csvFile.getName());
		if (errorList.isEmpty()) {
			log.warn("Возникли ошибки во время импорта файла " + csvFile.getName());
			return ResponseEntity.unprocessableEntity().body(errorList);
		}

		return ResponseEntity.ok(null);
	}

	/**
	 * Записывает данные о вакцинации в БД.
	 * При необходимости создаются вакцины, пункты вакцинаций
	 * @param dtos импортируемые данные
	 */
	@Transactional
	protected void importAll(Collection<VaccinationImportDto> dtos) {
		Map<String, VaccinationPoint> uuidPointMap = new HashMap<>();
		Map<String, Vaccine> nameVaccineMap = new HashMap<>();

		dtos.forEach(dto -> {
			VaccinationPoint vaccinationPoint = uuidPointMap.get(dto.getPointUUID());
			if (vaccinationPoint == null) {
				vaccinationPoint = pointService.findByUuid(dto.getPointUUID()).orElse(null);
				if (vaccinationPoint == null) {
					vaccinationPoint = pointService.createFromVaccinationImportDto(dto);
				}
				uuidPointMap.put(dto.getPointUUID(), vaccinationPoint);
			}

			Vaccine vaccine = nameVaccineMap.get(dto.getVaccine());
			if (vaccine == null) {
				vaccine = vaccineService.findByNameOrCreate(dto.getVaccine());
				nameVaccineMap.put(vaccine.getName(), vaccine);
			}

			vaccinationService.createFromVaccinationImportDto(dto, vaccine, vaccinationPoint);
		});
	}

	/**
	 * Проверка импортируемых данных на соответствие ФИО+паспорт.
	 * Непрошедшие проверку строки удаляются из множества импортируемых
	 * @param importSet множество импортируемых строк
	 * @param errors список ошибок
	 */
	private void deleteNotValid(Set<VaccinationImportDto> importSet, List<String> errors) {
		Set<VaccinationImportDto> notValid = new HashSet<>();
		importSet.forEach(dto -> {
			//валидация имя + паспорт
			boolean valid
					= personClient.verify(dto.getFirstName(), dto.getLastName(), dto.getPatronymic(), dto.getPassport());

			if (!valid) {
				String error = String.format("Вакцинированный %s %s %s с пасспортом %d не найден. Информация о нем не загружена",
						dto.getFirstName(), dto.getLastName(),
						StringUtils.hasLength(dto.getPatronymic()) ? dto.getPatronymic() : "",
						dto.getPassport());
				log.warn("Ошибка импорта: " + error);
				errors.add(error);
				notValid.add(dto);
			}
		});
		importSet.removeAll(notValid);
	}
}
