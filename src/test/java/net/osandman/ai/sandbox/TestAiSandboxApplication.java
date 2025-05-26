package net.osandman.ai.sandbox;

import org.springframework.boot.SpringApplication;

public class TestAiSandboxApplication {

	public static void main(String[] args) {
		SpringApplication.from(AiSandboxApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
