package com.example.demo.controller;

import com.example.demo.repository.MocksRepository;
import com.example.demo.repository.domain.MockConfiguration;
import com.example.demo.repository.domain.MockResponse;
import com.example.demo.request.RequestData;
import com.example.demo.service.MockResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController()
public class MocksController {
    @Autowired
    private MocksRepository repository;

    @Autowired
    private MockResponseService mockResponseService;

    @RequestMapping(value = "/{userId}/paths/**", method = RequestMethod.GET)
    public final void mockGet(@PathVariable("userId")String userId, HttpServletRequest request, HttpServletResponse response) throws IllegalAccessException, IOException {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String requestURI = request.getRequestURI().replace("/" + userId, "");

/*        if(!auth.getName().equalsIgnoreCase(userId)) {
            throw new IllegalAccessException("Access denied for path and user:" + auth.getName());
        }*/

        List<MockConfiguration> mocks = this.repository.findByPathAndRequestMethodAndAccountId(requestURI,
                RequestMethod.GET, userId);
        if (CollectionUtils.isEmpty(mocks)) {
            throw new IllegalAccessException("Unrecognized path");
        }

        Optional<MockResponse> mockResponse = mockResponseService.getResponse(mocks,
                new RequestData("", request.getQueryString(), RequestMethod.GET.name(), request.getContentType()));

        if (mockResponse.isPresent()) {
            response.setContentType(mockResponse.get().getMediaType());
            response.setStatus(mockResponse.get().getHttpStatus());
            response.getOutputStream().write(mockResponse.get().getValue().getBytes());
        } else {
            throw new IllegalStateException("Unable fo find mock response");
        }
    }

    @RequestMapping(value = "/{userId}/paths/**", method = RequestMethod.POST)
    public final void mockPost(@PathVariable("userId")String userId, HttpServletRequest request, HttpServletResponse response) throws IllegalAccessException, IOException {
        String requestURI = request.getRequestURI().replace("/" + userId, "");

        List<MockConfiguration> mocks = this.repository.findByPathAndRequestMethodAndAccountId(requestURI,
                RequestMethod.POST, userId);
        if (CollectionUtils.isEmpty(mocks)) {
            throw new IllegalAccessException("Invalid path");
        }
        String body = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        Optional<MockResponse> mockResponse = mockResponseService.getResponse(mocks,
                new RequestData(body, request.getQueryString(), RequestMethod.POST.name(), request.getContentType()));

        if (mockResponse.isPresent()) {
            response.setContentType(mockResponse.get().getMediaType());
            response.setStatus(mockResponse.get().getHttpStatus());
            response.getOutputStream().write(mockResponse.get().getValue().getBytes());
        } else {
            throw new IllegalStateException("Unable fo find mock response");
        }
    }
}