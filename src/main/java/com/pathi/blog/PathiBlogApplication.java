package com.pathi.blog;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "PATHI Blog App Rest API's",
                description = "PATHI Blog App Rest API's documentation",
                version = "v1.0",
                contact = @Contact(
                        name = "Shashi kumar Pathi",
                        email = "skpathi.shashi@gmail,com",
                        url = "https://spring.io/projects/spring-boot"
                ),
                license = @License(
                        name = "Apache 2.0"
                )
        ),
        externalDocs = @ExternalDocumentation(
                description = "PATHI Blog App Rest API's documentation",
                url = "https://github.com/pshashik"
        )
)
public class PathiBlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(PathiBlogApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }


}
