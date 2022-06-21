package ru.tfs.commontypes.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.SneakyThrows;
import org.apache.kafka.common.serialization.Deserializer;

/** Преобразует сообщение из kafka в объект */
public class VaccinationTransportDtoDeserializer implements Deserializer<VaccinationTransportDto> {
	@SneakyThrows
	@Override
	public VaccinationTransportDto deserialize(String s, byte[] bytes) {
		return bytes == null
				? null
				: new ObjectMapper()
				.registerModule(new JavaTimeModule())
				.readValue(bytes, VaccinationTransportDto.class);
	}
}
