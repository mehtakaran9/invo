package com.invo.service;

import com.invo.dto.Response;
import com.invo.model.Item;

public interface ItemService {
    Response getItems(String request);

    Response addItem(Item item);

    Response getAllItems();
}
