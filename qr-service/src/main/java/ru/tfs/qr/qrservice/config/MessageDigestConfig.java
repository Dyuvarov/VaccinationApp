package ru.tfs.qr.qrservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/** Конфигруация бина для получения хэша вакцинации */
@Configuration
public class MessageDigestConfig {
	@Bean
	public MessageDigest messageDigest() throws NoSuchAlgorithmException {
		return MessageDigest.getInstance("MD5");
	}
}
