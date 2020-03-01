package com.invo.dal;

import com.invo.model.Item;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

@Service
public interface ItemDAL extends MongoRepository<Item, String> {
}
