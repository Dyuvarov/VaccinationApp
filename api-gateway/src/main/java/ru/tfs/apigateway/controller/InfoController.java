package ru.tfs.apigateway.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.tfs.apigateway.dto.PersonInfoDto;
import ru.tfs.apigateway.service.InfoService;

@RestController
@RequestMapping("/api/v1/info")
@RequiredArgsConstructor
public class InfoController {
	private final InfoService infoService;

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/{passport}")
	public PersonInfoDto getInfoByPassport(@PathVariable Integer passport) {
		return infoService.getPersonInfoByPassport(passport);
	}
}
