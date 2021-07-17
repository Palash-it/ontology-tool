package com.ebispot.palash.ontologyTools;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.SimpleCommandLinePropertySource;

@SpringBootApplication
public class OntologyToolsApplication {

  public static void main(String[] args) {
    SpringApplication app = new SpringApplication(OntologyToolsApplication.class);
    SimpleCommandLinePropertySource source = new SimpleCommandLinePropertySource(args);
    if (!source.containsProperty("spring.profiles.active")
        && !System.getenv().containsKey("SPRING_PROFILES_ACTIVE")) {
      app.setAdditionalProfiles("dev");
    }
    app.run(args);
  }

}
