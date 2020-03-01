package com.invo.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@Document
public class User implements Serializable {
    @Id
    private String username;
    private String password;
    private String name;
    private String deviceId;
    private boolean isLoggedIn;
    private String key;
}
