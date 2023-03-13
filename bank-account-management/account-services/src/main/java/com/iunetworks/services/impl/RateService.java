package com.iunetworks.services.impl;

import com.iunetworks.dtos.TransactionDto;
import com.iunetworks.enums.Currency;
import com.iunetworks.exceptions.ApplicationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;

@Service
public class RateService {

  private final WebClient.Builder webClientBuilder;

  @Autowired
  public RateService(@Qualifier("webClientBean") WebClient.Builder webClientBuilder) {
    this.webClientBuilder = webClientBuilder;
  }

  public BigDecimal getTransferAmount(Currency out, Currency in, BigDecimal amount) {
    TransactionDto block = webClientBuilder
      .baseUrl("https://v6.exchangerate-api.com/")
      .build()
      .get()
      .uri(String.format("/v6/e5aa05974335e1698dfdd5f8/pair/%s/%s/%f", out.name(), in.name(), amount))
      .retrieve()
      .bodyToMono(TransactionDto.class)
      .block();
    if (block == null) {
      throw new ApplicationException(HttpStatus.SERVICE_UNAVAILABLE, "Unavailable");
    }
    return block.getConversionResult();

  }

}
