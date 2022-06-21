package ru.tfs.qr.qrservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.tfs.qr.qrservice.dto.QrCodeResponseDto;
import ru.tfs.qr.qrservice.dto.ValidationResponseDto;
import ru.tfs.qr.qrservice.service.VaccinationInfoService;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class VaccinationInfoController {

	private final VaccinationInfoService	vaccinationInfoService;

	@GetMapping("/{passport}")
	@ResponseStatus(HttpStatus.OK)
	public QrCodeResponseDto getNewestQrByDocument(@PathVariable Integer passport) {
		return vaccinationInfoService.findNewestQrByDocument(passport);
	}

	@GetMapping("/check")
	@ResponseStatus(HttpStatus.OK)
	public ValidationResponseDto checkQr(@RequestParam String code) {
		return vaccinationInfoService.validateQrCode(code);
	}
}
