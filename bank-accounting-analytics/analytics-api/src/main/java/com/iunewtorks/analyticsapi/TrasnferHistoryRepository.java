package com.iunewtorks.analyticsapi;

import main.com.iunetworks.TransferHistory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TrasnferHistoryRepository extends MongoRepository<TransferHistory,String> {
    Optional<TransferHistory> getByFromAccountNumber(String fromAccountNumber);

}
