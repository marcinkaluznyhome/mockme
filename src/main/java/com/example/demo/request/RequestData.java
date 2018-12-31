package com.example.demo.request;

public class RequestData {
    String body;
    String queryString;
    String httpMethod;
    String contentType;

    public RequestData(String body, String queryString, String httpMethod, String contentType) {
        this.body = body;
        this.queryString = queryString;
        this.httpMethod = httpMethod;
        this.contentType = contentType;
    }

    public String getContentType() {
        return contentType;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public String getBody() {
        return body;
    }

    public String getQueryString() {
        return queryString;
    }
}
