package com.crio.coder_hack.repository;

import com.crio.coder_hack.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User,String> {

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);
}
