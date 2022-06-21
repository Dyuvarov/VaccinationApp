package ru.tfs.apigateway.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;
import ru.tfs.apigateway.config.FeignClientConfig;


@ReactiveFeignClient(name="medical", configuration = FeignClientConfig.class,
		fallback = MedicalReactiveFeignClientFallback.class)
public interface MedicalReactiveFeignClient {
	@GetMapping("/medical/vaccination/{document}")
	Mono<Object> vaccinationByDocument(@PathVariable Integer document);
}

@Component
@Slf4j
class MedicalReactiveFeignClientFallback implements MedicalReactiveFeignClient {

	@Override
	public Mono<Object> vaccinationByDocument(Integer document) {
		log.error("Fallback: Ошибка при попытке получить информацию о вакцинации по пассорту: " + document);
		return Mono.empty();
	}
}
