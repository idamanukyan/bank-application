package com.iunetworks;

import com.iunetworks.bootstrap.UserManagementApplicationBootstrap;
import com.iunetworks.util.admin.CreateAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableEurekaClient
public class BankUserManagementApplication implements CommandLineRunner {

  private UserManagementApplicationBootstrap bootstrap;

  private CreateAdmin createAdmin;

  public static void main(String[] args) {
    SpringApplication.run(BankUserManagementApplication.class, args);
  }

  @Override
  public void run(String... args) {
    this.bootstrap.init();
    //this.createAdmin.createAdmin();
  }

  @Autowired
  public void setBootstrap(UserManagementApplicationBootstrap bootstrap, CreateAdmin createAdmin) {
    this.bootstrap = bootstrap;
    this.createAdmin = createAdmin;
  }


}
