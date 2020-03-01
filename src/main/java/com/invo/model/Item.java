package com.invo.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Document
public class Item implements Serializable {
    @Id
    @Size(min = 10, max = 10, message = "stockId must be 10 characters long")
    private String stockId;
    private long stock;
    @NotEmpty(message = "itemName cannot be empty")
    private String itemName;
    private List<Transaction> transactionList = new ArrayList<>();
}
