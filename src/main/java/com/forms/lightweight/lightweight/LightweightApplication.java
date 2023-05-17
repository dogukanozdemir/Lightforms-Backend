package com.forms.lightweight.lightweight;

import com.forms.lightweight.lightweight.config.ApplicationConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(value = ApplicationConfig.class)
public class LightweightApplication {

	public static void main(String[] args) {
		SpringApplication.run(LightweightApplication.class, args);
	}

}
