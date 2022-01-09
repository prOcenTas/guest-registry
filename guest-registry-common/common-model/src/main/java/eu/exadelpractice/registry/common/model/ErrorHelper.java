package eu.exadelpractice.registry.common.model;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class ErrorHelper {
	@Value("${spring.application.name}")
	private String app;

	public String createErrorId() {
		return app + "_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy_MM_dd_hh_mm_ss_SSS")) + "_"
				+ UUID.randomUUID().toString().replace('-', '_');
	}
}
