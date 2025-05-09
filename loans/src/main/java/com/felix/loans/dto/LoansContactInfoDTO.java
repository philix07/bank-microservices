package com.felix.loans.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;


@ConfigurationProperties(prefix = "cards")
@Getter
@Setter
public class LoansContactInfoDTO {

  String message;
  Map<String, String> contactDetails;
  List<String> onCallSupport;

}
