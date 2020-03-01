package com.invo.impl;

import com.invo.dal.ItemDAL;
import com.invo.dal.impl.ItemDALImpl;
import com.invo.dto.Response;
import com.invo.model.Item;
import com.invo.service.ItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.security.SecureRandom;
import java.util.List;

import static com.invo.util.IdGenerator.generateRandomString;

@Slf4j
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemDALImpl itemDAL;

    @Autowired
    private ItemDAL itemDALRepo;

    @Override public Response getItems(String request) {
        Response response = new Response();
        List<Item> repoResponse = itemDAL.findItemsByString(request);
        if (CollectionUtils.isEmpty(repoResponse)) {
            response.setErrorCode("NOT_FOUND");
            response.setErrorMessage("Item not found. Please check your request");
            log.error("Item Not found {}", request);
            return response;
        }
        response.setResult(repoResponse);
        return response;
    }

    @Override public Response addItem(Item item) {
        Response response = new Response();
        if (!item.getItemName().isEmpty() && item.getStock() > 0) {
            item.setStockId(generateRandomString(10));
            itemDALRepo.save(item);
            response.setSuccess(true);
        } else {
            response.setErrorMessage("Invalid request to add items");
            response.setErrorCode("INVALID_REQUEST");
            response.setSuccess(false);
        }
        return response;
    }

    @Override public Response getAllItems() {
        Response response = new Response();
        response.setResult(itemDALRepo.findAll());
        response.setSuccess(true);
        return response;
    }


}
