package ru.tfs.qr.qrservice.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import ru.tfs.commontypes.kafka.VaccinationTransportDto;
import ru.tfs.commontypes.kafka.VaccinationTransportDtoDeserializer;

import java.util.HashMap;

@Configuration
public class KafkaConsumerConfig {

	@Value("${kafka.bootstrap-server}")
	private String bootstrapServer;

	@Value("${kafka.groupId}")
	private String groupId;

	@Bean
	public ConsumerFactory<String, VaccinationTransportDto> consumerFactory() {
		var config = new HashMap<String, Object>();
		config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
		config.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
		config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, VaccinationTransportDtoDeserializer.class);
		return new DefaultKafkaConsumerFactory<>(config);
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, VaccinationTransportDto> kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, VaccinationTransportDto> factory =
				new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory());
		factory.setConcurrency(1);
		return factory;
	}
}
