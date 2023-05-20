package com.iunetworks.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class AdminService {
  private final WebClient.Builder webClientBuilder;

  @Autowired
  public AdminService(WebClient.Builder webClientBuilder) {
    this.webClientBuilder = webClientBuilder;
  }

  public String getAllAccountsInProcess() {
    String token=((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
      .getHeader("Authorization");
    try {
      String accounts = webClientBuilder
        .baseUrl("http://bank-account-management/")
        .build().get()
        .uri("/admin/allaccountsinprocess")
        .header("Authorization", token)
        .retrieve()
        .bodyToMono(String.class)
        .block();
      assert accounts != null;
      return accounts;
    } catch (Exception e) {
      return null;
    }

  }

  public String getAllCardsInProcess() {
    String token=((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
      .getHeader("Authorization");
    try {
      String accounts = webClientBuilder
        .baseUrl("http://bank-account-management/")
        .build().get()
        .uri("/admin/allcardsinprocess")
        .header("Authorization", token)
        .retrieve()
        .bodyToMono(String.class)
        .block();
      assert accounts != null;
      return accounts;
    } catch (Exception e) {
      return null;
    }

  }


}
