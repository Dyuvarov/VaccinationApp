package ru.tfs.apigateway.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;
import ru.tfs.apigateway.config.FeignClientConfig;

@ReactiveFeignClient(name="qr-code-service", configuration = FeignClientConfig.class,
		fallback = QrReactiveFeignClientFallback.class)
public interface QrReactiveFeignClient {
	@GetMapping("/qr/{passport}")
	Mono<Object> getNewestQrByDocument(@PathVariable Integer passport);
}

@Component
@Slf4j
class QrReactiveFeignClientFallback implements QrReactiveFeignClient {

	@Override
	public Mono<Object> getNewestQrByDocument(Integer passport) {
		log.error("Fallback: Ошибка при попытке получить информацию о qr по пассорту: " + passport);
		return Mono.empty();
	}
}
