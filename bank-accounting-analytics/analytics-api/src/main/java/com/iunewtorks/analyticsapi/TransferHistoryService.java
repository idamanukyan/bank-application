package com.iunewtorks.analyticsapi;

import main.com.iunetworks.TransferHistory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TransferHistoryService {

    private final MongoOperations mongoOperations;

    private final TrasnferHistoryRepository trasnferHistoryRepository;

    public TransferHistoryService(MongoOperations mongoOperations , TrasnferHistoryRepository trasnferHistoryRepository) {
        this.mongoOperations = mongoOperations;
        this.trasnferHistoryRepository = trasnferHistoryRepository;
    }

    public void saveTransferHistory(TransferHistory transferHistory) {
        mongoOperations.save(transferHistory, "transferHistory");
        System.out.println("Transfer  : " + transferHistory);
    }

    public LocalDateTime getTransferHistory() {
        // return mongoOperations.collectionExists("transferHistory");
        //return mongoOperations.getCollection("transferHistory").withDocumentClass(TransferHistory.class).getDocumentClass().getName();
        return trasnferHistoryRepository.getByFromAccountNumber("000112219510788").get().getTransferDate();

    }


}
