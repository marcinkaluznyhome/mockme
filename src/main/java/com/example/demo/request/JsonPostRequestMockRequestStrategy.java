package com.example.demo.request;

import com.example.demo.repository.domain.MockConfiguration;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;

@Component
public class JsonPostRequestMockRequestStrategy implements MockRequestStrategy {
    @Override
    public boolean isApplicable(MockConfiguration configuration, RequestData requestData) {
        return requestData.getContentType().equalsIgnoreCase(configuration.getContentType())
                && "POST".equalsIgnoreCase(configuration.getRequestMethod())
                && !StringUtils.isEmpty(requestData.getBody())
                && MediaType.APPLICATION_JSON_VALUE.equalsIgnoreCase(requestData.getContentType());
    }

    @Override
    public Object resolve(RequestData request) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readTree(request.getBody());
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }
}
