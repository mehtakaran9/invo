package com.invo.impl;

import com.invo.dal.UserDAL;
import com.invo.dto.Response;
import com.invo.model.User;
import com.invo.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {
    private static final String KEY = "abracadabra123";
    @Autowired
    private UserDAL userDAL;

    @Override public Response login(User user, HttpServletRequest request) {
        Response response = new Response();
        Optional<User> userObject = userDAL.findByUsername(user.getUsername());
        if(!userObject.isPresent()) {
            response.setSuccess(false);
            response.setErrorCode("USER_NOT_FOUND");
            response.setErrorMessage("User could not be found");
            return response;
        }
        User userRepo = userObject.get();
        if (!StringUtils.isEmpty(userRepo.getPassword()) && userRepo.getPassword()
            .equals(user.getPassword()) && userRepo.getUsername().equals(user.getUsername())) {
            user.setLoggedIn(true);
            if (!StringUtils.isEmpty(userRepo.getDeviceId())) {
                user.setDeviceId(userRepo.getDeviceId());
            }
            if(!StringUtils.isEmpty(userRepo.getKey())) {
                user.setKey(userRepo.getKey());
            }
            userDAL.save(user);
            response.setSuccess(true);
            log.info("User logged in {}", user.getUsername());
            return response;
        }
        setFailedResponse(response);
        return response;
    }

    @Override public Response register(User user, HttpServletRequest request) {
        Response response = new Response();
        Optional<User> userRepo = userDAL.findByUsername(user.getUsername());
        if (userRepo.isPresent()) {
            response.setErrorMessage("User already present");
            response.setErrorCode("ALREADY_REGISTERED");
            log.info("User {} not created due to already registered. Redirecting to login", user);
        } else if (!StringUtils.isEmpty(user.getKey()) && !user.getUsername().isEmpty() && !user
            .getPassword().isEmpty()) {
            if (user.getKey().equals(KEY)) {
                user.setDeviceId(request.getHeader("deviceId"));
                userDAL.save(user);
                log.info("Successfully created a new user {}", user);
            }
        }
        return login(user, request);
    }

    private void setFailedResponse(Response response) {
        response.setSuccess(false);
        response.setErrorCode("FAILED");
        response.setErrorMessage("FAILED");
    }

}
