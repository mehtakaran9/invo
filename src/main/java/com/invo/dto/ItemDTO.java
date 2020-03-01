package com.invo.dto;

import com.invo.model.Transaction;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ItemDTO {
    private long stock;
    private String itemName;
    private List<Transaction> transactionList = new ArrayList<>();
}
