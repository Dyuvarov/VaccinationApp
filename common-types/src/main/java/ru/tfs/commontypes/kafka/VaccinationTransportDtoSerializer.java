package ru.tfs.commontypes.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.SneakyThrows;
import org.apache.kafka.common.serialization.Serializer;

import java.nio.charset.StandardCharsets;

public class VaccinationTransportDtoSerializer implements Serializer<VaccinationTransportDto> {

	@SneakyThrows
	@Override
	public byte[] serialize(String topic, VaccinationTransportDto data) {
		return data == null
				? null
				: new ObjectMapper()
					.registerModule(new JavaTimeModule())
					.writeValueAsString(data)
					.getBytes(StandardCharsets.UTF_8);
	}
}
