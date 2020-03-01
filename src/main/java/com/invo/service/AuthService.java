package com.invo.service;

import com.invo.dto.Response;
import com.invo.model.User;

import javax.servlet.http.HttpServletRequest;

public interface AuthService {
    Response login(User user, HttpServletRequest request);

    Response register(User user, HttpServletRequest request);
}
