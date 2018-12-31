package com.example.demo.request;

import com.example.demo.repository.domain.MockConfiguration;

public interface MockRequestStrategy {

    boolean isApplicable(MockConfiguration configuration, RequestData requestData);

    Object resolve(RequestData request);
}
