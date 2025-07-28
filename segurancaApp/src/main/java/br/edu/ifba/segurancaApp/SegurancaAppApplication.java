package br.edu.ifba.segurancaApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class SegurancaAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SegurancaAppApplication.class, args);
	}

}
