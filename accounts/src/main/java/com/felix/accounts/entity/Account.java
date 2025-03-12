package com.felix.accounts.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "accounts")
public class Account extends BaseEntity {

  @Id
  private Long accountNumber;

  private Long customerId;

  private String accountType;

  private String branchAddress;

}

