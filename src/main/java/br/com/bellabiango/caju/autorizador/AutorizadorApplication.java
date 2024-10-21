package br.com.bellabiango.caju.autorizador;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
public class AutorizadorApplication {

	public static void main(String[] args) {
		SpringApplication.run(AutorizadorApplication.class, args);
	}

}
