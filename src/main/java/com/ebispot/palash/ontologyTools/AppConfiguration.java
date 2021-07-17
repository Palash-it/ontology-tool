package com.ebispot.palash.ontologyTools;

import java.time.Duration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfiguration {

  @Bean
  public RestTemplate restTemplate(RestTemplateBuilder builder) {
    return builder.setConnectTimeout(Duration.ofMillis(5000))
        .setReadTimeout(Duration.ofMillis(5000)).build();
  }

}
