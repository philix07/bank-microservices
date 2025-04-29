package com.felix.accounts;

import com.felix.accounts.dto.AccountsContactInfoDTO;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableConfigurationProperties(value = {AccountsContactInfoDTO.class})
@EnableFeignClients
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@OpenAPIDefinition(
  info = @Info(
    title = "Account microservice REST API Documentation",
    description = "MyBank 'Account' Microservice REST API Documentation",
    version = "v1",
    contact = @Contact(
      name = "Felix Liando", // the author we can contact if there's any problem with the docs
      email = "felix.liando07@gmail.com",
      url = "https://www.linkedin.com/in/felix-liando-324306250/" // link where people can reach or contact me
    )
  ),
  externalDocs = @ExternalDocumentation(
    description = "External Documentation - MyBank microservice REST API Documentation (Testing Purpose, Dummy URL)",
    url = "https://www.linkedin.com/in/felix-liando-324306250/"
  )
)
@SpringBootApplication
public class AccountApplication {

  public static void main(String[] args) {
    SpringApplication.run(AccountApplication.class, args);
  }

}
