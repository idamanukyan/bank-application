package com.iunetworks.controllers;

import com.iunetworks.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/admin")

public class AdminController {
  private final AdminService adminService;

  @Autowired
  public AdminController(AdminService adminService) {
    this.adminService = adminService;
  }

  @GetMapping("/accounts")
  @PreAuthorize("hasAnyAuthority('can_create_admin')")
  public String getAllAccountsInProcess() {
    return adminService.getAllAccountsInProcess();
  }

  @GetMapping("/cards")
  @PreAuthorize("hasAnyAuthority('can_create_admin')")
  public String getAllCardsInProcess() {
    return adminService.getAllCardsInProcess();
  }

  @PreAuthorize("hasAnyAuthority('can_create_admin')")
  @PutMapping("/accountreject/{id}")
  public void rejectAccounts(@PathVariable UUID id){
    adminService.rejectAccount(id);
  }
  @PreAuthorize("hasAnyAuthority('can_create_admin')")
  @PutMapping("/accountaccept/{id}")
  public void acceptAccounts(@PathVariable UUID id){
    adminService.acceptAccount(id);
  }
  @PreAuthorize("hasAnyAuthority('can_create_admin')")
  @PutMapping("/cardreject/{id}")
  public void rejectCard(@PathVariable UUID id){
    adminService.rejectCard(id);
  }
  @PreAuthorize("hasAnyAuthority('can_create_admin')")
  @PutMapping("/cardaccept/{id}")
  public void acceptCard(@PathVariable UUID id){
    adminService.acceptCard(id);
  }

  @PreAuthorize("hasAnyAuthority('can_create_admin')")
  @PutMapping("/carddelete/{id}")
  public void cardDelet(@PathVariable UUID id){
    adminService.cardDelete(id);
  }

  @PreAuthorize("hasAnyAuthority('can_create_admin')")
  @PutMapping("/accountdelete/{id}")
  public void accountDelete(@PathVariable UUID id){
    adminService.accountDelete(id);
  }
}
