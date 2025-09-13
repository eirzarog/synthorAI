package dev.eirzarog.synthor.api;

import dev.eirzarog.synthor.api.entities.User;
import dev.eirzarog.synthor.api.repositories.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackageClasses = User.class)
@EnableJpaRepositories(basePackageClasses = {UserRepository.class})
public class SynthorApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SynthorApiApplication.class, args);
    }

}
