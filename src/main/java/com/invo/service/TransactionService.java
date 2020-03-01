package com.invo.service;

import com.invo.dto.Response;
import com.invo.model.Transaction;

public interface TransactionService {

    Response createNewTransaction(Transaction transaction);
}
