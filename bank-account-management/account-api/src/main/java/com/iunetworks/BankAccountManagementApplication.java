package com.iunetworks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class BankAccountManagementApplication {

  public static void main(String[] args) {
    SpringApplication.run(BankAccountManagementApplication.class, args);
  }

}
