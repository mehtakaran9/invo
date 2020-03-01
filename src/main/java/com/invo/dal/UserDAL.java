package com.invo.dal;

import com.invo.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDAL extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);
}
