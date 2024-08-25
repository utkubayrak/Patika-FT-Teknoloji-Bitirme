package com.utkubayrak.jetbusservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class JetbusServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(JetbusServiceApplication.class, args);
	}

}
