package com.dva.springboot.app;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.FileSystemUtils;

@SpringBootApplication
public class SprinbootAppiCrudApplication implements CommandLineRunner {

	@Autowired 
	private BCryptPasswordEncoder  passwordEncoderC;
	
	public static void main(String[] args) {
		SpringApplication.run(SprinbootAppiCrudApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		FileSystemUtils.deleteRecursively(Paths.get("uploads").toFile());
		Files.createDirectories(Paths.get("uploads"));
		String contrasenia = "12345";
		for(int i = 0 ; i < 2 ; i++ ) {
			String bcript = passwordEncoderC.encode(contrasenia);
			System.err.println(bcript);
		}
		
	}

}
