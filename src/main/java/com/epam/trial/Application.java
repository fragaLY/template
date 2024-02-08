package com.epam.trial;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.info.BuildProperties;

@Slf4j
@SpringBootApplication
public class Application {

  public static void main(String[] args) {
    final var context = SpringApplication.run(Application.class, args);
    final var properties = context.getBean(BuildProperties.class);
    log.info("[APPLICATION] Service version {}.", properties.getVersion());
  }

}
