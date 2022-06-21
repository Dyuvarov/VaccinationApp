package ru.tfs.spring.data.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class ExceptionControllerAdvice {

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<?> handleNotFoundException(EntityNotFoundException ex) {
		log.error(ex.getMessage(), ex);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	}

	@ExceptionHandler(Throwable.class)
	public ResponseEntity<?> handleException(Throwable throwable) {
		String message = String.format("%s: %s", LocalDateTime.now(), throwable.getMessage());
		log.error(message, throwable);
		return ResponseEntity.badRequest().body(Map.of("error", message));
	}
}
