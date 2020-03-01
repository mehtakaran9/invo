package com.invo.dal.impl;

import com.invo.dal.ItemDAL;
import com.invo.model.Item;
import com.invo.model.Transaction;
import com.mongodb.client.result.UpdateResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
@Slf4j
public class ItemDALImpl {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Item> findItemsByString(String request) {
        Query query = new Query();
        query.addCriteria(Criteria.where("stockId").is(request)
            .orOperator(Criteria.where("itemName").is(request)));
        return mongoTemplate.find(query, Item.class);
    }

    public UpdateResult findAndModifyItem(Transaction transaction) {
        Query query = new Query();
        query.addCriteria(Criteria.where("stockId").is(transaction.getStockId()));
        query.addCriteria(Criteria.where("quantity").gte(transaction.getQuantity()));
        Update update = new Update();
        update.inc("quantity", -transaction.getQuantity());
        update.push("transactionList", transaction);
        return mongoTemplate.updateFirst(query, update, Item.class);
    }
}
