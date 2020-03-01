package com.invo.impl;

import com.invo.dal.ItemDAL;
import com.invo.dal.impl.ItemDALImpl;
import com.invo.dto.Response;
import com.invo.model.Transaction;
import com.invo.service.TransactionService;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import static com.invo.util.IdGenerator.generateRandomString;


@Component
@Transactional
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private ItemDALImpl itemDAL;

    @Override public Response createNewTransaction(Transaction transaction) {
        Response response = new Response();
        transaction.setTransactionId(generateRandomString(8));
        transaction.setTransactionDate(new Date());
        if(!transaction.getStockId().isEmpty() && !transaction.getUsername().isEmpty()) {
            UpdateResult updateResult = itemDAL.findAndModifyItem(transaction);
            if(updateResult != null) {
                response.setSuccess(updateResult.getModifiedCount() == 1);
            } else {
                response.setSuccess(false);
            }
            return response;
        }
        response.setErrorCode("NOT_UPDATED");
        response.setErrorMessage("Could not update");
        return response;
    }
}
