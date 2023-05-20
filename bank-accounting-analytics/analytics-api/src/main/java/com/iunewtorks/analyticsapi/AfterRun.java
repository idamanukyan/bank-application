package com.iunewtorks.analyticsapi;

import main.com.iunetworks.TransferHistory;
import main.com.iunetworks.enums.Currency;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;



@Component
public class AfterRun {

    private final TransferHistoryService transferHistoryService;
    private final RabbitMQJsonConsumer rabbitMQJsonConsumer;

    public AfterRun(TransferHistoryService transferHistoryService, RabbitMQJsonConsumer rabbitMQJsonConsumer) {
        this.transferHistoryService = transferHistoryService;
        this.rabbitMQJsonConsumer = rabbitMQJsonConsumer;
    }


    @EventListener(ApplicationReadyEvent.class)
    public void ready() {

//        TransferHistory transferHistory = new TransferHistory("1", "000112219510788", "000112242736545", new BigDecimal(100),
//                new BigDecimal(16068.573858), new BigDecimal(16168.573858), Currency.USD,
//                new BigDecimal(2404.2451), new BigDecimal(2403.9922), Currency.AMD,
//                LocalDateTime.now(), new BigDecimal(0.2529));
//
//        transferHistoryService.saveTransferHistory(transferHistory);
//        System.out.println(transferHistoryService.getTransferHistory());
        //consumeMessageService.rec();
        //receiver.receivedMessage(transferHistory);
    }


}
