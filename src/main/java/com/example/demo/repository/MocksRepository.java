package com.example.demo.repository;

import com.example.demo.repository.domain.MockConfiguration;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

public interface MocksRepository extends MongoRepository<MockConfiguration, String> {

    List<MockConfiguration> findByPathAndRequestMethodAndAccountId(String path, RequestMethod requestMethod, String accountId);

    List<MockConfiguration> findByAccountId(String accountId);
}
