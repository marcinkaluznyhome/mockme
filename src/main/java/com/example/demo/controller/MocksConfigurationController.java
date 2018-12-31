package com.example.demo.controller;

import com.example.demo.repository.MocksRepository;
import com.example.demo.repository.domain.MockConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin("*")
public class MocksConfigurationController {
    @Autowired
    private MocksRepository repository;

    @GetMapping("/configurations/{id}")
    public ResponseEntity get(@PathVariable("id") String id) throws IllegalAccessException {
        MockConfiguration configuration = validateAccess(id);
        return ResponseEntity.ok(configuration);
    }

    @GetMapping("/configurations")
    public final ResponseEntity getAll() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(repository.findByAccountId(auth.getName()));
    }

    @PostMapping("/configurations")
    public final MockConfiguration post(@RequestBody MockConfiguration mockConfiguration) throws IllegalAccessException {
        if (!StringUtils.isEmpty(mockConfiguration.getId())) {
            validateAccess(mockConfiguration.getId());
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        mockConfiguration.setAccountId(auth.getName());
        return repository.save(mockConfiguration);
    }

    @DeleteMapping("/configurations/{id}")
    public final void delete(@PathVariable("id") String id) throws IllegalAccessException {
        MockConfiguration configuration = validateAccess(id);
        repository.delete(configuration);
    }


    private MockConfiguration validateAccess(String id) throws IllegalAccessException {
        Optional<MockConfiguration> configuration = repository.findById(id);
        boolean hasAccess = false;
        if (configuration.isPresent()) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            hasAccess = auth.getName().equalsIgnoreCase(configuration.get().getAccountId());
        }

        if (!hasAccess) {
            throw new IllegalAccessException("Unable to access configuration");
        }

        return configuration.get();
    }
}