package ru.tfs.spring.web.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import ru.tfs.commontypes.kafka.VaccinationTransportDto;

import ru.tfs.commontypes.kafka.VaccinationTransportDtoSerializer;

import java.util.HashMap;

@Configuration
public class KafkaProducerConfig {
	@Value("${kafka.bootstrap-server}")
	private String bootstrapServer;

	@Bean
	public ProducerFactory<String, VaccinationTransportDto> producerFactory() {
		var configProps = new HashMap<String, Object>();
		configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
		configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, VaccinationTransportDtoSerializer.class);
		return new DefaultKafkaProducerFactory<>(configProps);
	}

	@Bean
	public KafkaTemplate<String, VaccinationTransportDto> kafkaTemplate() {
		return new KafkaTemplate<>(producerFactory());
	}
}
