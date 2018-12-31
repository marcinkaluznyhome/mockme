package com.example.demo.request;

import com.example.demo.repository.domain.MockConfiguration;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
public class GetRequestMockRequestStrategy implements MockRequestStrategy {
    @Override
    public boolean isApplicable(MockConfiguration configuration, RequestData requestData) {
        return requestData.getContentType().equalsIgnoreCase(configuration.getContentType())
                && "GET".equalsIgnoreCase(configuration.getRequestMethod())
                && MediaType.APPLICATION_JSON_VALUE.equalsIgnoreCase(requestData.getContentType());
    }

    @Override
    public Object resolve(RequestData request) {
        return request.getQueryString();
    }
}
