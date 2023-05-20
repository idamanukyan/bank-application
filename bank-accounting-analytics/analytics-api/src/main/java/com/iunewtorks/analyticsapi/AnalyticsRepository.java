package main.com.iunetworks.repositories;

import main.com.iunetworks.TransferHistory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnalyticsRepository extends MongoRepository<TransferHistory,String> {
}
