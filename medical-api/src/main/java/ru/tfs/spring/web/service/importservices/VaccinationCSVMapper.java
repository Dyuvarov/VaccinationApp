package ru.tfs.spring.web.service.importservices;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.tfs.spring.web.CSVHeader;
import ru.tfs.spring.web.dto.VaccinationImportDto;

import javax.validation.constraints.NotNull;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/** Преобразует данные в формате CSV в dto импорта */
@Component
public class VaccinationCSVMapper {

	public Set<VaccinationImportDto> processMultipartFile(MultipartFile csvFile, List<String> errorList) throws IOException {
		Set<VaccinationImportDto> importSet = new HashSet<>();

		Reader fileReader = new BufferedReader(new InputStreamReader(csvFile.getInputStream(), StandardCharsets.UTF_8));
		List<CSVRecord> records = CSVFormat.RFC4180
				.withHeader(CSVHeader.class)
				.parse(fileReader)
				.getRecords();
		if (CollectionUtils.isEmpty(records)) {
			errorList.add("Пустой файл импорта");
		}

		if (errorList.isEmpty()) {
			importSet = records.stream()
					.map(record -> csvRecordToImportDto(record, errorList))
					.collect(Collectors.toSet());
		}
		return importSet;
	}

	private VaccinationImportDto csvRecordToImportDto(CSVRecord record, List<String> errors) {
		VaccinationImportDto vaccinationImportDto = new VaccinationImportDto();

		Arrays.stream(VaccinationImportDto.class.getDeclaredFields())
				.forEach(field -> {
					field.setAccessible(true);
					try {
						field.set(vaccinationImportDto, extractValueFromRecord(field, record, errors));
					} catch (IllegalAccessException e) {
						errors.add("Ошибка доступа к полю " + field.getName());
					}
				});
		return vaccinationImportDto;
	}

	private Object extractValueFromRecord(Field field, CSVRecord record, List<String> errors) {
		var type = field.getType();
		Object value = null;
		if (type.equals(String.class)) {
			value = extractString(field.getName(), record, errors);
		} else if(type.equals(Integer.class)) {
			value = extractInt(field.getName(), record, errors);
		} else if (type.equals(LocalDateTime.class)) {
			value = extractDate(field.getName(), record, errors);
		} else {
			throw new IllegalArgumentException(String.format("Неподдерживаемый тип %s", type.getSimpleName()));
		}

		if (value == null && field.isAnnotationPresent(NotNull.class)) {
			errors.add(
					String.format("В строке %d не заполнено обязательное поле %s",
							record.getRecordNumber(), field.getName())
			);
			return null;
		}

		return value;
	}

	private String extractString(String field, CSVRecord record, List<String> errors) {
		String value = record.get(field);
		if (!StringUtils.hasLength(value)) {
			return null;
		}
		return value;
	}

	private Integer extractInt(String field, CSVRecord record, List<String> errors) {
		String valueStr = extractString(field, record, errors);
		if (valueStr == null) {
			return null;
		}

		Integer value = null;
		try {
			value = Integer.parseInt(valueStr);
		} catch (NumberFormatException e) {
			errors.add(
					String.format("В строке %d неверный формат записи числа в поле %s",
							record.getRecordNumber(), field)
			);
		}
		return value;
	}

	private LocalDateTime extractDate(String field, CSVRecord record, List<String> errors) {
		String valueStr = extractString(field, record, errors);
		if (valueStr == null) {
			return null;
		}

		LocalDateTime value = null;
		try {
			value = LocalDateTime.parse(valueStr);
		} catch (DateTimeParseException e) {
			errors.add(
					String.format("В строке %d неверный формат записи даты в поле %s",
							record.getRecordNumber(), field)
			);
		}
		return value;
	}
}
