package ru.tfs.apigateway.config;

import feign.Response;
import feign.RetryableException;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FeignErrorDecoder implements ErrorDecoder {

	private final ErrorDecoder defaultErrorDecoder = new Default();

	@Override
	public Exception decode(String s, Response response) {
		Exception exception = defaultErrorDecoder.decode(s, response);

		log.error("Ошибка обращения к сервису через feign-клиент. url: " + response.request().url(), exception);
		if (response.status() == 503
				|| response.status() == 504) {
			return new RetryableException(
					response.status(),
					"Сервис недоступен",
					response.request().httpMethod(),
					null,
					response.request()
			);
		}
		return exception;
	}
}
