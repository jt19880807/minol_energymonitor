package com.minol.energymonitor;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.minol.energymonitor.dao")
public class MinolEnergymonitorApplication {

	public static void main(String[] args) {
		SpringApplication.run(MinolEnergymonitorApplication.class, args);
	}
}
