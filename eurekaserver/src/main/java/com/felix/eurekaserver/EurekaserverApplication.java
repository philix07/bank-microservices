package com.felix.eurekaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
// Using this annotation we're converting from a normal springboot project
// to act as a Service Discovery agent with the help of Eureka library
@EnableEurekaServer
public class EurekaserverApplication {

  public static void main(String[] args) {
    SpringApplication.run(EurekaserverApplication.class, args);
  }

}
