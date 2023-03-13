package com.iunetworks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.UUID;

@Service
public class AdminService {
  private final WebClient.Builder webClientBuilder;
  private final ServiceFeignClient serviceFeignClient;

  @Autowired
  public AdminService(WebClient.Builder webClientBuilder, ServiceFeignClient serviceFeignClient) {
    this.webClientBuilder = webClientBuilder;
    this.serviceFeignClient = serviceFeignClient;
  }

  public String getAllAccountsInProcess() {
    return serviceFeignClient.getAllAccountsInProcess();
  }

  public String getAllCardsInProcess() {
    return serviceFeignClient.getAllCardsInProcess();
  }

  public void rejectAccount(UUID uuid) {
    serviceFeignClient.rejectAccounts(uuid);
  }

  public void acceptAccount(UUID uuid) {
    serviceFeignClient.acceptAccount(uuid);

  }

  public void rejectCard(UUID uuid) {
    serviceFeignClient.rejectCard(uuid);
  }

  public void acceptCard(UUID uuid) {
    serviceFeignClient.cardAccept(uuid);
  }

  public void cardDelete(UUID uuid) {
    serviceFeignClient.cardDelete(uuid);
  }

  public void accountDelete(UUID uuid) {
    serviceFeignClient.accountDelete(uuid);
  }
//  public String getAllCardsInProcess() {
//    String token=((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
//      .getHeader("Authorization");
//    try {
//      String accounts = webClientBuilder
//        .baseUrl("http://bank-account-management/")
//        .build().get()
//        .uri("/admin/allcardsinprocess")
//        .header("Authorization", token)
//        .retrieve()
//        .bodyToMono(String.class)
//        .block();
//      assert accounts != null;
//      return accounts;
//    } catch (Exception e) {
//      return null;
//    }
//
//  }


}
