package com.iunewtorks.analyticsapi;

import main.com.iunetworks.TransferHistory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
public class RabbitMQJsonConsumer implements Serializable {
    private final TransferHistoryService transferHistoryService;
    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQJsonConsumer.class);

    public RabbitMQJsonConsumer(TransferHistoryService transferHistoryService) {
        this.transferHistoryService = transferHistoryService;
    }

    @RabbitListener(queues = {"${rabbitmq.queue.json.name}"})
    public void consumeJsonMessage(TransferHistory transferHistory){
        LOGGER.info(String.format("Received JSON message -> %s", transferHistory.toString()));
        transferHistoryService.saveTransferHistory(transferHistory);
    }
}