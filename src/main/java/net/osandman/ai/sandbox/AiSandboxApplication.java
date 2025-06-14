package net.osandman.ai.sandbox;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class AiSandboxApplication {

    public static void main(String[] args) {
        SpringApplication.run(AiSandboxApplication.class, args);
    }

}
