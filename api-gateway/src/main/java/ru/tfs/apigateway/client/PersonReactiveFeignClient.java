package ru.tfs.apigateway.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;
import ru.tfs.apigateway.config.FeignClientConfig;

/** Клиент для вызова методов сервиса person */
@ReactiveFeignClient(name="person", configuration = FeignClientConfig.class,
		fallback = PersonReactiveClientFallback.class)
public interface PersonReactiveFeignClient {
	@GetMapping("/person/info/{passport}")
	Mono<Object> personInfoByPassport(@PathVariable Integer passport);
}

/** Обработка ответов, содержащих ошибку */
@Slf4j
@Component
class PersonReactiveClientFallback implements PersonReactiveFeignClient {

	@Override
	public Mono<Object> personInfoByPassport(@PathVariable Integer passport) {
		log.error("Fallback: Ошибка при попытке получить информацию о person по пассорту: " + passport);
		return Mono.empty();
	}
}
