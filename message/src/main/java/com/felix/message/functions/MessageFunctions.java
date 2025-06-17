package com.felix.message.functions;

import com.felix.message.dto.AccountsMsgDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
public class MessageFunctions {

  private static final Logger log = LoggerFactory.getLogger(MessageFunctions.class);

  // Actually we can simply write these 2 functions below as one function, but since
  // we're experimenting with Spring Cloud Function's feature, we're separating them.

  // By creating this function inside Spring Cloud Functions,
  // i can access the endpoint (API) without creating REST controller.
  // By simply invoking "http://localhost:port_number/email" i can trigger this function
  @Bean
  public Function<AccountsMsgDTO, AccountsMsgDTO> email() {
    // Function<Input Type, Return Type>
    return accountsMsgDTO -> {
      log.info("Sending email to the end user with the details : " + accountsMsgDTO.toString());
      return accountsMsgDTO;
    };
  }

  // Function<Input Type, Return Type>
  @Bean
  public Function<AccountsMsgDTO, Long> sms() {
    return accountsMsgDTO -> {
      log.info("Sending sms to the end user with the details : " + accountsMsgDTO.toString());
      return accountsMsgDTO.accountNumber();
    };
  }

  // There are 2 options other than Function which is:
  // Consumer<T> -> <T> is the type of the input to the operation.
  // Consumer is used when the business logic doesn't require an output or return value.
  //
  // Supplier<R> -> <R> is the type of results supplied by this supplier.
  // Supplier is used when the business logic doesn't require an input but returns an output.
}
