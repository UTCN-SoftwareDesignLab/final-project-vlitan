package main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan
@SpringBootApplication
@EnableAutoConfiguration
@EnableJpaRepositories(basePackages = {"main.repository"})
@PropertySource(value = "classpath:application.properties")
public class Main {//TODO add cache decorator
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
