package com.iunetworks;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.UUID;

@FeignClient(name = "bank-account-management")

public interface ServiceFeignClient {
  @GetMapping("/admin/allaccountsinprocess")
   String getAllAccountsInProcess();

  @GetMapping("/admin/allcardsinprocess")
   String getAllCardsInProcess();

  @PutMapping("/admin/accountreject/{id}")
  void rejectAccounts(@PathVariable UUID id);

  @PutMapping("/admin/accountaccept/{id}")
  void acceptAccount(@PathVariable UUID id);

  @PutMapping("/admin/cardreject/{id}")
  void rejectCard(@PathVariable UUID id);
  @PutMapping("/admin/cardaccept/{id}")
  void cardAccept(@PathVariable UUID id);

  @PutMapping("/admin/carddelete/{id}")
    void cardDelete(@PathVariable UUID id);

  @PutMapping( "/accountdelete/{id}")
  void accountDelete(@PathVariable UUID id);
}