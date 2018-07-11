package com.check.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAutoConfiguration
//@ImportResource({"classpath:spring-dubbo.xml"})
@SpringBootApplication
public class CheckServerServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CheckServerServerApplication.class, args);
	}
}
