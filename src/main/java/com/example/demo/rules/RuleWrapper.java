package com.example.demo.rules;

import com.example.demo.repository.domain.MockResponse;

import java.util.Map;

public class RuleWrapper {
    private Object request;
    private Map<String, MockResponse> responses;

    public RuleWrapper(Object request, Map<String, MockResponse> responses) {
        this.request = request;
        this.responses = responses;
    }

    public Object getRequest() {
        return request;
    }

    public void setRequest(Object request) {
        this.request = request;
    }

    public Map<String, MockResponse> getResponses() {
        return responses;
    }

    public void setResponses(Map<String, MockResponse> responses) {
        this.responses = responses;
    }
}
