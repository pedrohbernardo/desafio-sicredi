package com.sicredi.projetosicredi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ProjetoSicrediApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjetoSicrediApplication.class, args);
	}

}
