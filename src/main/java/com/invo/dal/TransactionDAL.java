package com.invo.dal;

import com.invo.model.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TransactionDAL extends MongoRepository<Transaction, String> {
}
