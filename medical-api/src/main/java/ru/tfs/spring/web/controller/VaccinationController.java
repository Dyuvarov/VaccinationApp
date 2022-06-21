package ru.tfs.spring.web.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tfs.spring.web.entity.Vaccination;
import ru.tfs.spring.web.service.VaccinationService;

/** Контроллер работы с сущностью Vaccination */
@RestController
@RequestMapping("/vaccination")
@AllArgsConstructor
public class VaccinationController {

	private final VaccinationService	vaccinationService;

	@GetMapping("/{document}")
	@ResponseStatus(HttpStatus.OK)
	public Vaccination vaccinationByDocument(@PathVariable Integer document) {
		return vaccinationService.getVaccinationByDocument(document);
	}
}
