package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import com.example.demo.security.service.RoleService;
import com.example.demo.security.service.UserService;
import com.example.demo.service.CategoryService;
import com.example.demo.service.ProductService;

@SpringBootApplication
public class DemoApplication extends SpringBootServletInitializer implements CommandLineRunner {

	@Autowired
	ProductService productservice;

	@Autowired
	CategoryService categoryservice;

	@Autowired
	UserService userservice;

	@Autowired
	RoleService roleservice;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		categoryservice.initialCharge();
		productservice.initialCharge();
		roleservice.initialCharge();
		userservice.initialCharge();
	}

}
