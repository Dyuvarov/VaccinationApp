package ru.tfs.spring.web.client;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ru.tfs.spring.web.config.FeignClientConfig;

/** Клиент для вызова методов сервиса person */
@FeignClient(name="person", configuration = FeignClientConfig.class, fallback = PersonClientFallback.class)
public interface PersonFeignClient {
	@GetMapping("/person/verify")
	Boolean verify(@RequestParam String firstName, @RequestParam String lastName,
				   @RequestParam(required = false) String patronymic, @RequestParam Integer passport);
}

/** Обработка ответов, содержащих ошибку */
@Slf4j
@Component
class PersonClientFallback implements PersonFeignClient {

	@Override
	public Boolean verify(String firstName, String lastName, String patronymic, Integer passport) {
		log.error("Fallback: Ошибка при попытке валидации person+passport");
		return false;
	}
}
