package com.oeindevelopteam.tasknavigator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TaskNavigatorApplication {

  public static void main(String[] args) {
    SpringApplication.run(TaskNavigatorApplication.class, args);
  }

}
