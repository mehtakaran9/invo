package com.invo.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.Size;
import java.util.Date;

@Data
public class Transaction {
    @Id
    @Size(min = 8, max = 8, message = "Size must be 8")
    private String transactionId;
    private String stockId;
    private int quantity;
    private long transactionAmount;
    private String soldBy;
    private Date transactionDate;
    private String soldTo;
    private String username;
}
