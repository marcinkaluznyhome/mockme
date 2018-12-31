package com.example.demo.repository;

import com.example.demo.repository.domain.Account;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AccountRepository extends MongoRepository<Account, String> {

    Account findByUsername(String username);

}