package com.invo.dal.impl;

import com.invo.dal.TransactionDAL;
import com.invo.model.Item;
import com.invo.model.Transaction;
import com.invo.util.IdGenerator;
import com.mongodb.client.result.UpdateResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class ItemDALImpl {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private TransactionDAL transactionDAL;

    public List<Item> findItemsByString(String request) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(request)
            .orOperator(Criteria.where("itemName").is(request)));
        return mongoTemplate.find(query, Item.class);
    }

    public UpdateResult findAndModifyItem(Transaction transaction) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(transaction.getStockId()));
        query.addCriteria(Criteria.where("stock").gte(transaction.getQuantity()));
        Item item = mongoTemplate.findOne(query, Item.class);
        if (item != null) {
            transaction.setTransactionId(IdGenerator.generateRandomString(8));
            transaction.setTransactionDate(new Date());
            transactionDAL.save(transaction);
            Update update = new Update();
            update.inc("stock", -transaction.getQuantity());
            update.push("transactionList", transaction);
            return mongoTemplate.updateFirst(query, update, Item.class);
        } else {
            return null;
        }
    }
}
