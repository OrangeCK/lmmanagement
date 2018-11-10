package com.ck.lmmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@PropertySource("classpath:config/sys.properties")
public class LmmanagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(LmmanagementApplication.class, args);
	}
}
