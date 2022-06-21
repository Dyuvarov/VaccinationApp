package ru.tfs.spring.web.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.tfs.spring.web.service.importservices.ImportService;

import java.util.List;

/** Контроллер загрузки данных */
@RestController
@RequestMapping("/")
@AllArgsConstructor
public class ImportController {

	private final ImportService importService;

	@RequestMapping("/process-file")
	public ResponseEntity<List<String>> processCSV(@RequestParam MultipartFile data) {
		return importService.processCSV(data);
	}
}
