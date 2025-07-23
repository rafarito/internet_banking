package br.com.ifba.emailmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class EmailmicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmailmicroserviceApplication.class, args);
	}

}
